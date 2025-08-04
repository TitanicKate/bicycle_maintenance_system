package com.titanic.bicycle_maintenance_system.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用API响应结果封装类
 * 统一接口返回格式
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 状态码：200表示成功，其他表示错误
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有化构造方法，防止直接实例化
     */
    private Result() {
    }

    /**
     * 成功响应（无数据）
     *
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return success(null, "操作成功");
    }

    /**
     * 成功响应（带消息）
     *
     * @param message 成功消息
     * @return Result对象
     */
    public static <T> Result<T> success(String message) {
        return success(null, message);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return success(data, "操作成功");
    }

    /**
     * 成功响应（带数据和消息）
     *
     * @param data    响应数据
     * @param message 响应消息
     * @return Result对象
     */
    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 错误响应（默认消息）
     *
     * @return Result对象
     */
    public static <T> Result<T> error() {
        return error("操作失败");
    }

    /**
     * 错误响应（带消息）
     *
     * @param message 错误消息
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    /**
     * 错误响应（带状态码和消息）
     *
     * @param code    错误状态码
     * @param message 错误消息
     * @return Result对象
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 未授权响应
     *
     * @return Result对象
     */
    public static <T> Result<T> unauthorized() {
        return error(401, "未授权访问，请先登录");
    }

    /**
     * 权限不足响应
     *
     * @return Result对象
     */
    public static <T> Result<T> forbidden() {
        return error(403, "权限不足，无法访问");
    }

    /**
     * 资源不存在响应
     *
     * @return Result对象
     */
    public static <T> Result<T> notFound() {
        return error(404, "请求的资源不存在");
    }
}
