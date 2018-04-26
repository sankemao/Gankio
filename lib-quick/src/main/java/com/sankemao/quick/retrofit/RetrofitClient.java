package com.sankemao.quick.retrofit;

import android.text.TextUtils;

import com.sankemao.quick.retrofit.ioc.BaseUrl;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:TODO
 * Create Time: 2018/3/30.10:54
 * Author:jin
 * Email:210980059@qq.com
 */
public class RetrofitClient {
    private static OkHttpClient mOkHttpClient;
    private static Retrofit.Builder mRetrofitBuilder;

    private static OkHttpClient getOkClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return mOkHttpClient;
    }

    private static Retrofit.Builder getRetrofitBuilder() {
        if (mRetrofitBuilder == null) {
            mRetrofitBuilder = new Retrofit.Builder()
                    .client(getOkClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        return mRetrofitBuilder;
    }


    public static <T> T getApi(Class<T> clazz) {
        BaseUrl clazzAnnotation = clazz.getAnnotation(BaseUrl.class);
        if (clazzAnnotation == null) {
            throw new IllegalArgumentException(clazz.getSimpleName() + "必须加上BaseUrl注解");
        }
        String baseUrl = clazzAnnotation.url();
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalArgumentException(clazz.getSimpleName() + "的@BaseUrl注解中必须设置url");
        }
        return getRetrofitBuilder().baseUrl(baseUrl).build().create(clazz);
    }

    public static <T> ObservableTransformer<BaseResult<T>, T> transformer(){
        return observable -> observable.flatMap(tBaseResult -> {
            if (tBaseResult.isOk) {
                return createObservable(tBaseResult.data);
            } else {
                return Observable.error(new ErrorHandle.ServiceError("", tBaseResult.message));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> IO_TRANSFORMER() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //将baseResult中的data -> Observable<T>
    private static <T> Observable<T> createObservable(T data) {
//        return Observable.create(observableEmitter -> {
//            observableEmitter.onNext(data);
//            observableEmitter.onComplete();
//        });
        return Observable.just(data);
    }

}
