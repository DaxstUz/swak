package com.example.httplib.http.core;

/**
 * Describe:网络响应基类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class BaseResponse {
    private String reMsg;
    private String reCode;

    public void setReMsg(String reMsg) {
        this.reMsg = reMsg;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getReMsg() {
        return reMsg;
    }

    public String getReCode() {
        return reCode;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "reMsg='" + reMsg + '\'' +
                ", reCode='" + reCode + '\'' +
                '}';
    }
}
