package com.kuo.huahua.utils;

/**
 * 响应结果生成工具
 * @author channing
 * @date 2020/12/17
 */
public class ResultGenerator {

    public static Result genSuccessResult() {
        return new Result()
                .setSuccess(Boolean.TRUE)
                .setCode(ErrorEnum.SUCCESS.getResultCode())
                .setMessage(ErrorEnum.SUCCESS.getResultMessage());
    }

    public static <T> Result genSuccessResult(T data) {
        return new Result()
                .setSuccess(Boolean.TRUE)
                .setCode(ErrorEnum.SUCCESS.getResultCode())
                .setMessage(ErrorEnum.SUCCESS.getResultMessage())
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setSuccess(Boolean.FALSE)
                .setCode(ErrorEnum.INTERNAL_SERVER_ERROR.getResultCode())
                .setMessage(message);
    }

    public static Result genFailResult(String code, String message) {
        return new Result()
                .setSuccess(Boolean.FALSE)
                .setCode(code)
                .setMessage(message);
    }

    public static Result genFailResult(ErrorInfo errorInfo) {
        return new Result()
                .setSuccess(Boolean.FALSE)
                .setCode(errorInfo.getResultCode())
                .setMessage(errorInfo.getResultMessage());
    }
}
