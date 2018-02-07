package sankemao.baselib.appupdate.download;

import android.support.annotation.NonNull;

import sankemao.baselib.appupdate.CallBacks;

/**
 * Description:下载工具接口.
 * Create Time:2017/11/27.23:24
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IDownload {
    /**
     * 下载
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull CallBacks.HttpDownloadCallback callback);
}
