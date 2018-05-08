package com.sunyard.sundemo.model.http.bean;

/**
 * Created by MengJie on 2017/11/27.
 */

public class RspBase<T> {
    public static final int CODE_SUCCESS = 0;

    public int code = -1;
    public String message = "";
    public T data;

    public boolean isSuccess(){
        return code == CODE_SUCCESS;
    }
}
