package sankemao.baselib.appupdate;

import sankemao.baselib.appupdate.download.IDownload;

/**
 * Description:apk下载的一些参数
 * Create Time: 2017/11/27.17:38
 * Author:jin
 * Email:210980059@qq.com
 */
public class DownOptions {

    //apk下载地址
    private String downUrl;

    //是否在通知栏上显示
    private boolean showNotification;

    //apk下载保存地址
    private String targetPath;

    //apk的md5值
    private String md5;

    //下载框架
    private IDownload mDownloadManager;

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public void setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public IDownload getDownloadManager() {
        return mDownloadManager;
    }

    public void setDownloadManager(IDownload downloadManager) {
        mDownloadManager = downloadManager;
    }
}
