package sankemao.framlib.http.download;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.callback.HttpDownloadCallback;

import java.io.File;
import java.text.DecimalFormat;

import sankemao.baselib.appupdate.CallBacks;
import sankemao.baselib.appupdate.download.IDownload;

/**
 * Description: 文件下载，基于okHttp,用于app更新
 * Create Time: 2018/2/6.14:50
 * Author:jin
 * Email:210980059@qq.com
 */
public class OkHttpDownload implements IDownload {

    private Context mContext;
    private DecimalFormat df = new DecimalFormat("0.00");

    public OkHttpDownload(Context context) {
        this.mContext = context;
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final CallBacks.HttpDownloadCallback callback) {
        GoHttp.with(mContext)
                .url(url)
                .download(new HttpDownloadCallback(path) {

                    @Override
                    public void onPreExecute() {
                        callback.onBefore();
                    }

                    @Override
                    public void onProgress(long current, long total) {
                        float orginProcess = (float) current / total;
                        //保留两位小数
                        String formatProcess = df.format(orginProcess);
                        callback.onProgress(Float.parseFloat(formatProcess), total);
                    }

                    @Override
                    public void onComplete(File saveFile) {
                        callback.onResponse(saveFile);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e.getMessage());
                    }
                });
    }
}
