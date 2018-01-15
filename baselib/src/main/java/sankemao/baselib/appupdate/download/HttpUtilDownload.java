package sankemao.baselib.appupdate.download;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.text.DecimalFormat;

import sankemao.baselib.appupdate.CallBacks;
import sankemao.baselib.http.HttpUtils;
import sankemao.baselib.http.callback.FileCallBack;

/**
 * Description: 使用httpUtils下载
 * Create Time: 2017/11/28.13:09
 * Author:jin
 * Email:210980059@qq.com
 */
public class HttpUtilDownload implements IDownload {
    private Context mContext;
    private DecimalFormat df = new DecimalFormat("0.00");

    public HttpUtilDownload(Context context) {
        this.mContext = context;
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final CallBacks.HttpDownloadCallback callback) {
        HttpUtils.with(mContext)
                .url(url)
                .downFile(new FileCallBack(path, fileName) {
                    @Override
                    public void onBefore() {
                        callback.onBefore();
                    }

                    @Override
                    public void onSuccess(File file) {
                        callback.onResponse(file);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        callback.onError(errorMsg);
                    }

                    @Override
                    public void onProgress(long currentBytes, long contentLength, boolean done) {
                        float orginProcess = (float) currentBytes / (float) contentLength;
                        //保留两位小数
                        String formatProcess = df.format(orginProcess);
                        callback.onProgress(Float.parseFloat(formatProcess), contentLength);
                    }
                });
    }

}
