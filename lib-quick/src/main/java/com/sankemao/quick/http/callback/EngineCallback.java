package com.sankemao.quick.http.callback;

import android.content.Context;

import java.util.Map;

/**
 * Created by jin on 2017/7/16.
 *
 */
public interface EngineCallback {

    /**
     * 在请求线程中
     */
    void onPreExecute(Context cxt, Map<String, Object> params);

    /**
     * 在主线程中
     */
    void onError(Exception e);

    /**
     * 在主线程中
     */
    void onSuccess(String result);

    //默认的
    EngineCallback Default_call_back = new EngineCallback() {
        @Override
        public void onPreExecute(Context cxt, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };

}
