package com.sankemao.quick.http.callback;

import java.io.InputStream;

/**
 * Description:TODO
 * Create Time: 2018/1/23.17:39
 * Author:jin
 * Email:210980059@qq.com
 */
public interface EngineDownloadCallback {

    EngineDownloadCallback DEFAULT_CALLBACK = new EngineDownloadCallback() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onResponse(InputStream inputStream, long contentLength) {

        }
    };

    void onPreExecute();

    void onError(Exception e);

    void onResponse(InputStream inputStream, long contentLength);
}
