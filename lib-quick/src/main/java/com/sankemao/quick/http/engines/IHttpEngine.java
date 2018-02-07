package com.sankemao.quick.http.engines;

import android.content.Context;

import com.sankemao.quick.http.callback.EngineCallback;
import com.sankemao.quick.http.callback.EngineDownloadCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jin on 2017/7/16.
 * 引擎的规范
 */
public interface IHttpEngine {

    //添加拦截器
    void addInterceptors(List<Interceptor> interceptor);

    //忽略https
    void supportHttps();

    //get异步请求
    void get(boolean cache, Context context, String url, Map<String, Object> params, Map<String, String> headers, EngineCallback callBack);

    //get同步请求.
    Response get(Context context, String url, Map<String, Object> params);

    //post异步请求
    void post(boolean cache, Context context, String url, Map<String, Object> params, EngineCallback callBack);

    //取消请求.
    void cancelRequest(Object tag);

    //下载文件
    void downFile(String url, EngineDownloadCallback callBack);

    //上传文件

    //https 添加证书.

    //设置超时
    void setConnTimeOut(int timeOut);

}
