package com.sankemao.quick.http.exception;

/**
 * description:
 * author: Darren on 2017/10/11 16:15
 * email: 240336124@qq.com
 * version: 1.0
 */
public class ServerDataTransformException extends Exception{

    public ServerDataTransformException(String exceptionText){
        this(new Throwable(exceptionText));
    }

    public ServerDataTransformException(Throwable cause) {
        super(cause);
    }

}
