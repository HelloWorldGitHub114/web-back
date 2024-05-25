package com.web10.music.commons.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回结果实现
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable, IResult<T> {

    /**
     * 200:成功   其他：失败
     */
    private String code;
    private String msg;
    private T data;

    public static Result ok() {
        return new Result(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    public static Result ok(String msg) {
        return new Result(ErrorCode.SUCCESS.getCode(), msg, null);
    }

    public static Result ok(String msg, Object data) {
        return new Result(ErrorCode.SUCCESS.getCode(), msg, data);
    }

    public static Result ok(Object data) {
        return new Result(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }


    public static Result error() {
        return new Result(ErrorCode.ERROR.getCode(), ErrorCode.ERROR.getMessage(), null);
    }

    public static Result error(ErrorCode errorCode) {
        return new Result(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }


    public static Result error(String code, String msg, Object data) {
        return new Result(code, ErrorCode.ERROR.getMessage(), data);
    }

    public static Result error(ErrorCode errorCode, String msg) {
        return new Result(ErrorCode.ERROR.getCode(), msg, null);
    }

    /**
     * 获得信息
     */
    @Override
    public String getMsg() {
        return this.msg;
    }

    /**
     * 获得状态码
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * 获得数据
     */
    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
