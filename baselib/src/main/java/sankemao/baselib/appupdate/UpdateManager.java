package sankemao.baselib.appupdate;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import sankemao.baselib.appupdate.download.IDownload;
import sankemao.baselib.appupdate.service.DownloadService;


/**
 * Description:TODO
 * Create Time: 2017/11/27.16:17
 * Author:jin
 * Email:210980059@qq.com
 */
public class UpdateManager {
    private Builder mBuilder;

    public UpdateManager(Builder builder) {
        this.mBuilder = builder;
    }

    /**
     * 开启服务进行apk下载
     * @param updateProgressCallback  下载监听回调
     */
    public void download(final CallBacks.UpdateProcessCallback updateProgressCallback) {
        DownloadService.bindService(mBuilder.mContext.getApplicationContext(), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ((DownloadService.DownloadBinder) service).start(mBuilder.mOptions, updateProgressCallback);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });
    }

    public static class Builder {
        private Context mContext;
        private DownOptions mOptions;

        public Builder() {
            mOptions = new DownOptions();
        }

        /**
         * 设置上下文
         */
        public Builder with(Context context) {
            this.mContext = context;
            return this;
        }

        /**
         * 是否显示在通知栏显示下载进度
         * 默认为显示
         */
        public Builder showNotification(boolean showNotification) {
            mOptions.setShowNotification(showNotification);
            return this;
        }

        /**
         * 设置下载地址
         */
        public Builder setDownUrl(String downUrl) {
            mOptions.setDownUrl(downUrl);
            return this;
        }

        /**
         * 设置保存地址
         */
        public Builder setTargetPath(String targetPath) {
            mOptions.setTargetPath(targetPath);
            return this;
        }

        /**
         * 设置下载工具
         */
        public Builder setDownloadManager(IDownload manager) {
            mOptions.setDownloadManager(manager);
            return this;
        }

        public UpdateManager build() {
            return new UpdateManager(this);
        }
    }
}
