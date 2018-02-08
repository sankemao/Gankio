package com.sankemao.quick.http.engines;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.sankemao.quick.http.GoHttpConfig;
import com.sankemao.quick.http.utils.HttpsSupport;
import com.sankemao.quick.http.utils.Utils;
import com.sankemao.quick.http.callback.EngineCallback;
import com.sankemao.quick.http.callback.EngineDownloadCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sankemao.baselib.data.datahandler.IOHandlerFactory;
import sankemao.baselib.data.datahandler.PreferencesIOHandler;

/**
 * Created by jin on 2017/7/16.
 * 默认引擎
 */
public class OkHttpEngine implements IHttpEngine {
    private static Handler mHandler = new Handler();

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    public void config(GoHttpConfig mDefaultConfig) {
        OkHttpClient.Builder newBuilder = mOkHttpClient.newBuilder();
        //拦截器
        if (mDefaultConfig.getInterceptors() != null && mDefaultConfig.getInterceptors().size() > 0) {
            for (Interceptor interceptor : mDefaultConfig.getInterceptors()) {
                newBuilder.addInterceptor(interceptor);
            }
        }

        //超时
        if (mDefaultConfig.getTimeOut() > 0) {
            newBuilder.connectTimeout(mDefaultConfig.getTimeOut(), TimeUnit.SECONDS);
        }

        //https支持
        if (mDefaultConfig.isSupportHttps()) {
            HttpsSupport.SSLParams sslParams = HttpsSupport.getSslSocketFactory();
            newBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        }

        mOkHttpClient = newBuilder.build();
    }

    /**
     * 下载文件
     */
    @Override
    public void downFile(String url, final EngineDownloadCallback downloadCallback) {
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        downloadCallback.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final InputStream inputStream = response.body().byteStream();
                final long length = response.body().contentLength();
                downloadCallback.onResponse(inputStream, length);
            }
        });
    }

    @Override
    public void post(final boolean cache, Context context, String url, Map<String, Object> params, final EngineCallback callBack) {
        //post缓存实现
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue() + "");
        }
        HttpUrl httpUrl = httpUrlBuilder.build();
        final String requestUrl = httpUrl.toString();
        LogUtils.e("post请求路径：" + requestUrl);

        //如果有缓存，先显示缓存
        if (cache) {
            final String cacheJson = IOHandlerFactory.INSTANCE.createIOHandler(PreferencesIOHandler.class).getString(requestUrl, "");
            if (!TextUtils.isEmpty(cacheJson)) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(cacheJson);
                    }
                });
            }
        }

        //正式请求
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(e);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resultJson = response.body().string();
//                        Log.e("Post返回结果: ", resultJson);
                        if (cache) {
                            final String cacheJson = IOHandlerFactory.INSTANCE.getSpHandler().getString(requestUrl, "");
                            if (resultJson.equals(cacheJson)) {
                                return;
                            } else {
                                //与缓存不一致，那么就缓存下来
                                IOHandlerFactory.INSTANCE.getSpHandler().save(requestUrl, resultJson);
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(resultJson);
                            }
                        });
                    }
                }
        );
    }

    /**
     * 组装post请求参数body
     */
    private RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    /**
     * 添加参数.
     */
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
//                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * 异步get请求.
     */
    @Override
    public void get(final boolean cache, Context context, String url, Map<String, Object> params, Map<String, String> headers, final EngineCallback callBack) {
        // 请求路径  参数 + 路径代表唯一标识

        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue() + "");
        }
        HttpUrl httpUrl = httpUrlBuilder.build();
        final String requestUrl = httpUrl.toString();

//        Log.e("Get请求路径：", requestUrl);
        //先展示缓存
        if (cache) {
            final String cacheJson = IOHandlerFactory.INSTANCE.getSpHandler().getString(requestUrl, "");
            if (!TextUtils.isEmpty(cacheJson)) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(cacheJson);
                    }
                });
            }
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(requestUrl)
                .tag(context)
                .headers(Headers.of(headers));
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
//                LogUtils.d(resultJson);

                if (cache) {
                    String cacheJson = IOHandlerFactory.INSTANCE.getSpHandler().getString(requestUrl, "");
                    if (resultJson.equals(cacheJson)) {
                        return;
                    } else {
                        //与缓存不一致，那么就缓存下来
                        IOHandlerFactory.INSTANCE.getSpHandler().save(requestUrl, resultJson);
                    }
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(resultJson);
                    }
                });
            }
        });
    }

    /**
     * 同步get请求
     */
    @Override
    public Response get(Context context, String url, Map<String, Object> params) {
        final String finalUrl = Utils.jointParams(url, params);
//        Log.e("Get请求路径：", finalUrl);
        Request.Builder requestBuilder = new Request.Builder()
                .url(finalUrl)
                .tag(context);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据tag取消请求
     * 参照:
     * https://github.com/jeasonlzy/okhttp-OkGo/blob/1b2a7edd092b7dedf321a2c5911a3e132db82d9b/okgo/src/main/java/com/lzy/okgo/OkGo.java
     *
     * @param tag
     */
    @Override
    public void cancelRequest(Object tag) {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        synchronized (OkHttpEngine.class) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
}
