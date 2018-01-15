package sankemao.baselib.appupdate;

import java.io.File;

/**
 * Description:回调接口
 * Create Time:2017/11/27.23:03
 * Author:jin
 * Email:210980059@qq.com
 */
public interface CallBacks {
    /**
     * Http文件下载回调, 与downloadmanager之间的回调
     */
    interface HttpDownloadCallback {
        /**
         * 进度
         *
         * @param progress 进度0.00 - 0.50  - 1.00
         * @param total    文件总大小 单位字节
         */
        void onProgress(float progress, long total);

        /**
         * 错误回调
         *
         * @param error 错误提示
         */
        void onError(String error);

        /**
         * 结果回调
         *
         * @param file 下载好的文件
         */
        void onResponse(File file);

        /**
         * 请求之前
         */
        void onBefore();
    }


    /**
     * 更新进度回调
     * 与具体调用者之间的回调
     * 对调用者提供的包含文件下载进度的回调接口
     */
     interface UpdateProcessCallback {
        /**
         * 开始
         */
        void onStart();

        /**
         * 进度
         *
         * @param progress  进度 0.00 -1.00 ，总大小
         * @param totalSize 总大小 单位B
         */
        void onProgress(float progress, long totalSize);

        /**
         * 总大小
         *
         * @param totalSize 单位B
         */
        void setMax(long totalSize);

        /**
         * 下载完了
         *
         * @param file 下载的app
         * @return true ：下载完自动跳到安装界面，false：则不进行安装
         */
        boolean onFinish(File file);

        /**
         * 下载异常
         *
         * @param msg 异常信息
         */
        void onError(String msg);
    }

}
