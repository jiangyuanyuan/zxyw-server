package com.zxyw.common;

/**
 * Created by jiangyuanyuan on 23/11/17.
 */
public enum  ResponseCode {

    SUCCESS(0,"SUCCESS"),

    ERROR(1,"ERROR"),

    NEED_LOGIN(10,"NEED_LOGIN"),

    ILLEGAL_ARGUMENT(1,"ILLEGAL_ARGUMENT");

    private final int code;

    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
}
