package com.sankemao.quick.retrofit.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:TODO
 * Create Time: 2018/3/30.11:04
 * Author:jin
 * Email:210980059@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseUrl {
    String url();
}
