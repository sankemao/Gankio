package com.sankemao.quick.http.callback;

import android.content.Context;

import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.utils.Utils;
import com.sankemao.quick.http.converter.Converter;
import com.sankemao.quick.http.exception.ServerDataTransformException;

import java.util.Map;

/**
 * Created by jin on 2017/7/22.
 *
 */
public abstract class DefaultCallback<T> implements EngineCallback {

    @Override
    public void onPreExecute(Context cxt, Map<String, Object> params) {

    }

    @Override
    public void onSuccess(String result) {
        Converter.Factory factory = GoHttp.getDefaultConfig().getFactory();
        if (factory == null) {
            throw new IllegalArgumentException("数据解析转换错误，请在初始化HttpConfig中配置解析工厂");
        }

        try {
            Converter<String, T> converter = factory.responseConverter(Utils.analysisClazzInfo(this));
            T parseResult = converter.convert(result);
            onParseSuccess(parseResult);
        } catch (Exception e) {
            e.printStackTrace();
            serverTransformError(new ServerDataTransformException(e));
        }
    }

    /**
     * 返回可以直接操作的对象
     */
    public abstract void onParseSuccess(T result);

    private void serverTransformError(ServerDataTransformException exception) {

    }
}
