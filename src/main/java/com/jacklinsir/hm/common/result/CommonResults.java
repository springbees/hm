package com.jacklinsir.hm.common.result;

import com.jacklinsir.hm.model.SysUser;
import lombok.Data;
import org.apache.ibatis.annotations.Results;

import java.io.Serializable;
import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (通用响应结果类)
 * @Date 2019/12/31 21:05
 */
@Data
public class CommonResults<T> implements Serializable {


    /**
     * 响应的结果总数量
     */
    private Long count;
    /**
     * 响应代码
     */
    private Integer code;

    /**
     * 响应的结果信息
     */
    private String msg;

    /**
     * 返回一个集合结果集
     */
    private T data;

    public CommonResults() {
    }

    public CommonResults(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public CommonResults(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public CommonResults(Integer code, String msg, Long count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }


    /**
     * 返回成功并无数据传输
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResults<T> success() {
        return new CommonResults<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 传入信息并响应给前端
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> CommonResults<T> success(String msg) {
        return new CommonResults<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> CommonResults<T> success(ResponseCode resultCode, T data) {
        return new CommonResults<T>(resultCode.getCode(), data);
    }

    /**
     * 传入响应码给前端返回执行的状态和信息
     *
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> CommonResults<T> success(ResponseCode resultCode) {
        return new CommonResults<T>(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> CommonResults<T> success(T data) {
        return new CommonResults<T>(ResponseCode.SUCCESS.getCode(), data);
    }


    /* 分页数据传输的 成功返回 */
    public static <T> CommonResults<T> success(Integer count, T data) {
        return new CommonResults<T>(ResponseCode.TABLE_SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), (long) count, data);
    }

    public static <T> CommonResults<T> success(String msg, Integer count, T data) {
        return new CommonResults<T>(ResponseCode.TABLE_SUCCESS.getCode(), msg, (long) count, data);
    }

    public static <T> CommonResults<T> success(ResponseCode resultCode, Integer count, T data) {
        return new CommonResults<T>(resultCode.getCode(), resultCode.getMessage(), (long) count, data);
    }

    /**
     * 无数据传输的 失败返回
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResults<T> failure() {
        return new CommonResults<T>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMessage());
    }

    public static <T> CommonResults<T> failure(ResponseCode resultCode) {
        return new CommonResults<T>(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> CommonResults<T> failure(Integer code, String msg) {
        return new CommonResults<T>(code, msg);
    }

    public static CommonResults ok() {
        return new CommonResults();
    }

}
