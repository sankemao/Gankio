package com.sankemao.quick.http;

import android.content.Context;
import android.text.TextUtils;

import com.sankemao.quick.http.callback.EngineCallback;
import com.sankemao.quick.http.callback.EngineDownloadCallback;
import com.sankemao.quick.http.engines.IHttpEngine;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by jin on 2017/7/16.
 */
public class GoHttp {

    //默认的 HttpEngine 参数参数设置
    private static GoHttpConfig mDefaultConfig = null;
    //网络请求引擎
    private IHttpEngine mHttpEngine = null;

    //直接带参数, 链式调用
    private String mUrl;
    //请求方式.
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0012;

    private Context mContext;
    //参数
    private Map<String, Object> mParams;
    private Map<String, String> mHeaders;
    //是否缓存
    private boolean mCache;

    /**
     * 获取配置
     */
    public static GoHttpConfig getDefaultConfig() {
        return mDefaultConfig;
    }

    /**
     * 设置配置
     */
    public static void config(GoHttpConfig config) {
        mDefaultConfig = config;
    }

    /**
     * 切换引擎
     * 在使用过程中可以变更引擎
     */
    public GoHttp exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    private GoHttp(Context context) {
        mContext = context;
        mParams = new HashMap<>();
        mHeaders = new HashMap<>();
    }

    public static GoHttp with(Context context) {
        return new GoHttp(context);
    }

    public GoHttp url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 请求方式.
     */
    public GoHttp post() {
        mType = POST_TYPE;
        return this;
    }

    public GoHttp get() {
        mType = GET_TYPE;
        return this;
    }

    /**
     * 配置缓存.
     */
    public GoHttp cache(boolean isCache) {
        mCache = isCache;
        return this;
    }

    public GoHttp addHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    public GoHttp addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public GoHttp addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    /**
     * 同步
     * TODO:请求头
     */
    public Response execute() {
        if (mType == GET_TYPE) {
            return mHttpEngine.get(mContext, this.mUrl, this.mParams);
        }

        //post同步请求待完成.
        return null;
    }

    /**
     * 异步，默认回调
     */
    public void enqueue() {
        enqueue(null);
    }

    /**
     * 异步，自定义回调
     */
    public void enqueue(EngineCallback callBack) {
        if (callBack == null) {
            callBack = EngineCallback.Default_call_back;
        }

        prepare();

        callBack.onPreExecute(mContext, mParams);

        //判断执行方法
        if (mType == POST_TYPE) {
            mHttpEngine.post(mCache, mContext, this.mUrl, this.mParams, callBack);
        }

        if (mType == GET_TYPE) {
            mHttpEngine.get(mCache, mContext, this.mUrl, this.mParams, this.mHeaders, callBack);
        }
    }

    public void download(EngineDownloadCallback downloadCallback) {
        if (downloadCallback == null) {
            downloadCallback = EngineDownloadCallback.DEFAULT_CALLBACK;
        }

        prepare();
        downloadCallback.onPreExecute();
        mHttpEngine.downFile(this.mUrl, downloadCallback);
    }

//    public void upload()

    /**
     * 取消请求
     */
    public void cancelRequest() {
        mHttpEngine.cancelRequest(mContext);
    }

    /**
     * 初始化加入一些参数和网络引擎
     */
    private void prepare() {
        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("url不能为空");
        }

        if (mDefaultConfig != null) {
            if (mHttpEngine == null) {
                mHttpEngine = mDefaultConfig.getHttpEngine();
            }
            //添加公共参数
            mParams.putAll(mDefaultConfig.getPublicParams());
        }

        if (mHttpEngine == null) {
            throw new NullPointerException("第三方的引擎为空，请在Application中初始化");
        }
    }

}
