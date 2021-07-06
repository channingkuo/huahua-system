package com.kuo.huahua.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author channing
 * @date 2020/12/17
 */
public class Result<T> {
    private String code;
    private String message;
    private T data;
    private boolean success;

    public Result() {

    }

    public Result(ErrorInfo errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMessage();
    }

    public Result<?> setCode(String code){
        this.code = code;
        return this;
    }

    public String getCode(){
        return code;
    }

    public Result<?> setSuccess(Boolean success){
        this.success = success;
        return this;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Result<?> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<?> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
