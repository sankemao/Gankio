package com.sankemao.quick.retrofit;

/**
 * Description:TODO
 * Create Time: 2018/3/30.13:57
 * Author:jin
 * Email:210980059@qq.com
 */
public class ErrorHandle {

    public static class ServiceError extends Throwable{
        String errorCode;
        public ServiceError(String errorCode, String errorMsg) {
            super(errorMsg);
            this.errorCode = errorCode;
        }
    }
}
