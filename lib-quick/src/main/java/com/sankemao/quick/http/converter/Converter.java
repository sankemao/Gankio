package com.sankemao.quick.http.converter;

/**
 * description: 提供converter的工厂
 * author: Darren on 2017/10/18 09:51
 * email: 240336124@qq.com
 * version: 1.0
 */
public interface Converter<F, T> {
    //F->T
    T convert(F value);

    abstract class Factory {
        // 对请求响应的类型转换
        public abstract <T> Converter<String, T> responseConverter(Class<T> type);
    }
}
