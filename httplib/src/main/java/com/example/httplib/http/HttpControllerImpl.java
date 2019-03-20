package com.example.httplib.http;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.httplib.http.config.ErrorStatus;
import com.example.httplib.http.core.BaseResponse;

import org.xutils.http.RequestParams;

/**
 * Describe:HttpController类的拓展延伸
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public final class HttpControllerImpl {

    private String baseUrl;
    private String responseSucceedCode;

    private static volatile HttpControllerImpl instance;

    private HttpControllerImpl() {
    }

    static HttpControllerImpl getInstance() {
        if (instance == null) {
            synchronized (HttpControllerImpl.class) {
                if (instance == null) {
                    instance = new HttpControllerImpl();
                }
            }
        }
        return instance;
    }

    void setBaseUrl(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    void setResponseSucceedCode(String responseSucceedCode) {
        this.responseSucceedCode = responseSucceedCode;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    String getResponseSucceedCode() {
        return responseSucceedCode;
    }

    /**
     * 当请求时，可提前配置公共参数
     *
     * @param params params
     */
    public void onConfigParams(RequestParams params) {
        if (configParamsCallback != null) configParamsCallback.onConfigParams(params);
    }

    /**
     * 当errorStatus.getMessage()时，回调配置的对应错误信息
     *
     * @param errorStatus ErrorStatus
     */
    public String onConfigErrorMessage(ErrorStatus errorStatus) {
        return configErrorMessageCallback != null ? configErrorMessageCallback.onConfigErrorMessage(errorStatus) : null;
    }

    /**
     * 当服务器返回码与{@link #responseSucceedCode}不相同调用
     *
     * @param response response
     * @return 是否处理
     */
    boolean onResponseFailure(Activity activity, BaseResponse response) {
        return responseCallback != null && responseCallback.onResponseFailure(activity, response);
    }

    public boolean onResponseError(Throwable throwable, ErrorStatus errorStatus) {
        return responseCallback != null && responseCallback.onResponseError(throwable, errorStatus);
    }

    private ConfigParamsCallback configParamsCallback;
    private ConfigErrorMessageCallback configErrorMessageCallback;
    private ResponseCallback responseCallback;

    public void setConfigParamsCallback(ConfigParamsCallback callback) {
        this.configParamsCallback = callback;
    }

    public void setConfigErrorMessageCallback(ConfigErrorMessageCallback callback) {
        this.configErrorMessageCallback = callback;
    }

    public void setResponseCallback(ResponseCallback callback) {
        this.responseCallback = callback;
    }

    public interface ResponseCallback {
        boolean onResponseFailure(Activity activity, BaseResponse response);

        boolean onResponseError(Throwable throwable, ErrorStatus errorStatus);
    }

    public interface ConfigErrorMessageCallback {
        String onConfigErrorMessage(ErrorStatus errorStatus);
    }

    public interface ConfigParamsCallback {
        void onConfigParams(RequestParams params);
    }
}
