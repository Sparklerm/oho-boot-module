package com.oho.common.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oho.common.constant.ErrorCodeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 返回实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> extends BaseResult {

    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public Result(Integer code, String errorCode, String message, T data) {
        super(code, errorCode, message);
        this.data = data;
    }

//    public boolean success() {
//        return CODE_SUCCESS.equals(getCode());
//    }

    public boolean systemFail() {
        return CODE_SYSTEM_ERROR.equals(getCode());
    }

    public static <T> Result<T> ok() {
        return new Result<>(CODE_SUCCESS, "", null);
    }

    public static <T> Result<T> ok(String message) {
        return new Result<>(CODE_SUCCESS, message, null);
    }

    public static <T> Result<T> success() {
        return new Result<>(Integer.valueOf(ErrorCodeConstant.SUCCESS.getCode()), ErrorCodeConstant.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CODE_SUCCESS, MESSAGE_SUCCESS, data);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(CODE_SUCCESS, message, data);
    }

    public static <T> Result<T> error(String message) {
        return Result.error(CODE_SYSTEM_ERROR, null, message, null);
    }

    public static <T> Result<T> authError() {
        return Result.error(CODE_AUTH_ERROR, null, SYSTEM_AUTH_ERROR, null);
    }

    public static <T> Result<T> error(String errorCode, String message) {
        return Result.error(CODE_SYSTEM_ERROR, errorCode, message, null);
    }

    public static <T> Result<T> error(ErrorCodeConstant errorCode, String message) {
        return Result.error(CODE_SYSTEM_ERROR, errorCode.getCode(), message, null);
    }

    public static <T> Result<T> error(Integer code, String errorCode, String message, T data) {
        return new Result<>(code, errorCode, message, data);
    }

}

