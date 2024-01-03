package com.atguigu.common.exception;

public enum BizCodeEnume {
    UNKNOWN_EXCEPTION(10000,"Unknown exception"),
    VAIDLD_EXCEPTION(10001,"参数格式校验失败"),

    PRODUCT_UP_EXCEPTION(11000,"Product up exception");


    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    BizCodeEnume(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
