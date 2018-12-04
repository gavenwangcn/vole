package com.github.vole.common.utils;

import java.io.Serializable;


public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 200;

    public static final int FAIL = 500;

    public static final int NO_PERMISSION = 403;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static R<Boolean> rest(boolean result) {
        R<Boolean> r = new R<Boolean>();
        if (!result) {
            r.setCode(R.FAIL);
            r.setData(false);
        }
        return r;
    }
}
