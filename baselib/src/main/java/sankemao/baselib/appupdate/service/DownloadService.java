package sankemao.baselib.appupdate.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import java.io.File;

import sankemao.baselib.R;
import sankemao.baselib.appupdate.CallBacks;
import sankemao.baselib.appupdate.DownOptions;
import sankemao.baselib.appupdate.utils.AppUpdateUtils;


/**
 * Description:apk更新服务, 同时在通知栏上显示进度
 * Create Time: 2017/11/27.16:23
 * Author:jin
 * Email:210980059@qq.com
 */
public class DownloadService extends Service {
    //通知栏的id.
    private static final int NOTIFY_ID = 0;
    //服务运行标记
    public static boolean isRunning = false;
    //通知中显示进度
    private NotificationManager mNotificationManager;
    //是否在通知栏中显示下载进度
    private boolean mShowNotification;

    private NotificationCompat.Builder mBuilder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }

    /**
     * 启动下载服务
     */
    public static void bindService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        isRunning = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * binder, 可在connection回调中拿到该对象, 直接调用当中方法.
     */
    public class DownloadBinder extends Binder {

        /**
         * 开始下载
         *
         * @param options 新app信息
         * @param updateProgressCallback  下载回调
         */
        public void start(DownOptions options, CallBacks.UpdateProcessCallback updateProgressCallback) {
            startDownload(options, updateProgressCallback);
        }
    }

    private void startDownload(DownOptions options, CallBacks.UpdateProcessCallback updateProgressCallback) {
        mShowNotification = options.isShowNotification();
        String downUrl = options.getDownUrl();
        if (TextUtils.isEmpty(downUrl)) {
            String contentText = "新版本下载路径出错";
            stop(contentText);
            return;
        }
        //apk名字
        String apkName = AppUpdateUtils.getApkName(options);
        File appDir = new File(options.getTargetPath());
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        //开始下载
        //TODO:
        options.getDownloadManager().download(downUrl, options.getTargetPath(), apkName, new FileDownloadCallback(updateProgressCallback));
    }

    /**
     * 停止服务
     * @param contentText   停止原因
     */
    private void stop(String contentText) {
        if (mBuilder != null) {
            mBuilder.setContentTitle(AppUpdateUtils.getAppName(DownloadService.this)).setContentText(contentText);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(NOTIFY_ID, notification);
        }
        close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationManager = null;
    }

    /**
     * 文件下载回调实现
     */
    class FileDownloadCallback implements CallBacks.HttpDownloadCallback {
        private final CallBacks.UpdateProcessCallback mCallBack;

        int oldRate = 0;

        public FileDownloadCallback(@Nullable CallBacks.UpdateProcessCallback callback) {
            super();
            this.mCallBack = callback;
        }

        @Override
        public void onBefore() {
            //初始化通知栏
            setUpNotification();
            if (mCallBack != null) {
                mCallBack.onStart();
            }
        }

        @Override
        public void onProgress(float progress, long total) {
            //做一下判断，防止自回调过于频繁，造成更新通知栏进度过于频繁，而出现卡顿的问题。
            int rate = Math.round(progress * 100);
            if (oldRate != rate) {
                if (mCallBack != null) {
                    mCallBack.setMax(total);
                    mCallBack.onProgress(progress, total);
                }

                if (mBuilder != null) {
                    mBuilder.setContentTitle("正在下载：" + AppUpdateUtils.getAppName(DownloadService.this))
                            .setContentText(rate + "%")
                            .setProgress(100, rate, false)
                            .setWhen(System.currentTimeMillis());
                    Notification notification = mBuilder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    mNotificationManager.notify(NOTIFY_ID, notification);
                }

                //重新赋值
                oldRate = rate;
            }


        }

        @Override
        public void onError(String error) {
            //App前台运行
            if (mCallBack != null) {
                mCallBack.onError(error);
            }
            try {
                mNotificationManager.cancel(NOTIFY_ID);
                close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void onResponse(File file) {
            if (mCallBack != null) {
                if (!mCallBack.onFinish(file)) {
                    close();
                    return;
                }
            }

            //开始安装app.
            try {

                if (AppUpdateUtils.isAppOnForeground(DownloadService.this) || mBuilder == null) {
                    //App前台运行
                    mNotificationManager.cancel(NOTIFY_ID);
                    AppUpdateUtils.installApp(DownloadService.this, file);
                } else {
                    //App后台运行
                    //更新参数,注意flags要使用FLAG_UPDATE_CURRENT
                    Intent installAppIntent = AppUpdateUtils.getInstallAppIntent(DownloadService.this, file);
                    PendingIntent contentIntent = PendingIntent.getActivity(DownloadService.this, 0, installAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(contentIntent)
                            .setContentTitle(AppUpdateUtils.getAppName(DownloadService.this))
                            .setContentText("下载完成，请点击安装")
                            .setProgress(0, 0, false)
                            //                        .setAutoCancel(true)
                            .setDefaults((Notification.DEFAULT_ALL));
                    Notification notification = mBuilder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    mNotificationManager.notify(NOTIFY_ID, notification);
                }
                //下载完自杀
                close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    /**
     * 关闭下载服务.
     */
    private void close() {
        stopSelf();
        isRunning = false;
    }

    /**
     * 设置进度通知栏.
     */
    private void setUpNotification() {
        if (!mShowNotification) {
            return;
        }

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("开始下载")
                .setContentText("正在连接服务器")
                .setSmallIcon(R.mipmap.app_update_icon)
                .setLargeIcon(AppUpdateUtils.drawableToBitmap(AppUpdateUtils.getAppIcon(DownloadService.this)))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
    }

}
