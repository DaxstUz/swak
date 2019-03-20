package com.example.httplib.http.config;

import com.example.httplib.http.HttpController;

/**
 * Describe:网络请求错误码
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public enum ErrorStatus {
    /**
     * 无网络
     */
    HTTP_UNCONNECTED(1),
    /**
     * 连接超时
     */
    HTTP_TIMEOUT(2),
    /**
     * 网络异常
     */
    HTTP_EXCEPTION(3),
    /**
     * 数据异常
     */
    DATA_EXCEPTION(4),
    /**
     * 其他异常
     */
    OTHER_EXCEPTION(-1);

    private final int code;

    private String errorCode;

    ErrorStatus(int code) {
        this.code = code;
    }

    public ErrorStatus setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return 返回错误信息（回调配置中获取，如回调为空，则使用默认错误信息）
     */
    public String getMessage() {
        String message = HttpController.getController().onConfigErrorMessage(this);
        return message != null ? message : getDefaultMessage();
    }

    /**
     * @return 返回默认错误信息
     */
    public String getDefaultMessage() {
        switch (this) {
            case HTTP_UNCONNECTED:
                // 无网络
                return "网络不可用，请检查网络设置";
            case HTTP_TIMEOUT:
                // 连接超时
                return "连接超时，请检查网络设置";
            case HTTP_EXCEPTION:
                // 网络异常
                return "请求出错，找到祖传错误码 " + getErrorCode();
            case DATA_EXCEPTION:
                // 数据异常
                return "数据异常，请尝试重新获取";
            default:
                // 其他异常
                return "请求失败，请尝试重新获取";
        }
    }

    public String toast() {
        return getDefaultMessage();
    }

    public String empty() {
        switch (this) {
            case HTTP_UNCONNECTED:
                // 无网络
                return "网络不太好，头有点晕~让我缓缓。";
            case HTTP_TIMEOUT:
                // 连接超时
                return "网络不太好，头有点晕~让我缓缓。";
            case HTTP_EXCEPTION:
                // 网络异常
                return "哎呀~ " + getErrorCode() + "...";
            case DATA_EXCEPTION:
                // 数据异常
                return "找不到数据了,GG~";
            default:
                // 其他异常
                return "晕~ 请求失败了";
        }
    }

    public String refresh() {
        switch (this) {
            case HTTP_UNCONNECTED:
                // 无网络
                return "网络不可用，请检查一下吧";
            case HTTP_TIMEOUT:
                // 连接超时
                return "连接超时，请检查一下吧";
            case HTTP_EXCEPTION:
                // 网络异常
                return "请求出错，找到祖传错误码 " + getErrorCode();
            case DATA_EXCEPTION:
                // 数据异常
                return "数据异常，请尝试重新获取";
            default:
                // 其他异常
                return "请求失败，请尝试重新获取";
        }
    }
}