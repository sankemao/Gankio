package com.sankemao.quick.http;

import com.sankemao.quick.http.converter.Converter;
import com.sankemao.quick.http.converter.DefaultConvertFactory;
import com.sankemao.quick.http.engines.IHttpEngine;
import com.sankemao.quick.http.engines.OkHttpEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * description:
 * author: Darren on 2017/10/11 15:41
 * email: 240336124@qq.com
 * version: 1.0
 */
public class GoHttpConfig {
    //公共参数
    private Map<String, Object> publicParams;
    //引擎
    private IHttpEngine httpEngine;
    //解析工厂，gson
    private Converter.Factory factory;
    //支持https
    private boolean mIsSupportHttps;
    //添加拦截器
    private List<Interceptor> mInterceptors;
    //请求超时
    private int mTimeOut;

    private GoHttpConfig(Builder builder) {
        this.publicParams = builder.publicParams;
        this.httpEngine = builder.httpEngine;
        this.factory = builder.factory;
        this.mIsSupportHttps = builder.isSupportHttps;
        this.mInterceptors = builder.interceptors;
        this.mTimeOut = builder.timeout;
    }

    public IHttpEngine getHttpEngine() {
        return httpEngine;
    }

    public Map<String, Object> getPublicParams() {
        return publicParams;
    }

    public Converter.Factory getFactory() {
        return factory;
    }

    public boolean isSupportHttps() {
        return mIsSupportHttps;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public int getTimeOut() {
        return mTimeOut;
    }

    public static class Builder {
        Map<String, Object> publicParams;
        //默认采用okhttp
        IHttpEngine httpEngine = new OkHttpEngine();
        //默认response解析
        Converter.Factory factory = DefaultConvertFactory.create();
        boolean isSupportHttps;
        List<Interceptor> interceptors;
        int timeout = -1;

        public Builder() {
            publicParams = new HashMap<>();
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            this.factory = factory;
            return this;
        }

        public Builder publicParams(Map<String, Object> publicParams) {
            this.publicParams = publicParams;
            return this;
        }

        public Builder engine(IHttpEngine httpEngine) {
            this.httpEngine = httpEngine;
            return this;
        }

        public Builder supportHttps() {
            isSupportHttps = true;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }

            interceptors.add(interceptor);
            return this;
        }

        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public GoHttpConfig build() {
            return new GoHttpConfig(this);
        }
    }
}
