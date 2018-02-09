package com.sankemao.quick.http.converter;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * description: 提供jsonConverter.
 * author: Darren on 2017/10/18 10:23
 * email: 240336124@qq.com
 * version: 1.0
 */
public class DefaultConvertFactory extends Converter.Factory {
    @Override
    public <T> Converter<String, T> responseConverter(final Type type) {
        return new Converter<String, T>() {
            @Override
            public T convert(String value) {
                return new Gson().fromJson(value, type);
            }
        };
    }

    private DefaultConvertFactory() {

    }

    public static DefaultConvertFactory create() {
        return new DefaultConvertFactory();
    }
}
