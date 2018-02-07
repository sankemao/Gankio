package com.sankemao.quick.http.callback;

import com.blankj.utilcode.util.FileUtils;
import com.sankemao.quick.http.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Description: 带进度的下载回调
 * Create Time: 2018/2/7.14:47
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class HttpDownloadCallback implements EngineDownloadCallback{
    private String mFilePath;

    public HttpDownloadCallback(String filePath) {
        this.mFilePath = filePath;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onResponse(InputStream inputStream, long contentLength) {
        //创建file
        File saveFile = new File(mFilePath);
        FileUtils.createFileByDeleteOldFile(saveFile);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveFile);
            long sum = 0;
            byte[] buf = new byte[8192];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf, 0 , len);
                sum += len;
                onProgress(sum, contentLength);
            }
            fos.flush();
            onComplete(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        } finally {
            Utils.closeIo(inputStream);
            Utils.closeIo(fos);
        }
    }

    public abstract void onProgress(long current, long total);

    public abstract void onComplete(File saveFile);
}
