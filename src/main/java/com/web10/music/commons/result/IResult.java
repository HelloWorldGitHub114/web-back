package com.web10.music.commons.result;

/**
 * 返回结果接口
 **/
public interface IResult<T> {

    /**
     * 获得信息
     */
    String getMsg();

    /**
     * 获得状态码
     */
    String getCode();

    /**
     * 获得数据
     */
    T getData();
}
