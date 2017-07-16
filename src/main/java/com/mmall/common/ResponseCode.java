package com.mmall.common;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/7/16.
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(1, "NEED_LOGIN"),
    ILEEGAL_ARGUMENTS(3, "ILEEGAL_ARGUMENTS");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }
}
