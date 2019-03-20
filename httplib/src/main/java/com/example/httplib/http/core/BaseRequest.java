package com.example.httplib.http.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.example.httplib.http.HttpController;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Locale;

/**
 * Describe:网络请求基类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class BaseRequest {

    /**
     * post 请求
     *
     * @param params   params
     * @param callback callback
     */
    public <T extends BaseResponse> Callback.Cancelable post(RequestParams params, HttpCallback<T> callback) {
        Callback.Cancelable cancelable = x.http().post(params, callback);
        callback.setCallbackCancelable(cancelable);
        callback.setRequestParams(params);
        return cancelable;
    }

    /**
     * get 请求
     *
     * @param params   params
     * @param callback callback
     */
    public <T extends BaseResponse> Callback.Cancelable get(RequestParams params, HttpCallback<T> callback) {
        Callback.Cancelable cancelable = x.http().get(params, callback);
        callback.setCallbackCancelable(cancelable);
        return cancelable;
    }

    /**
     * 获取完整的网络请求连接
     *
     * @param uri 地址url
     * @return 完整的连接
     */
    private String getUri(String uri) {
        String baseUrl = HttpController.getController().getBaseUrl();
        return baseUrl += uri;
    }

    /**
     * 获取默认参数
     *
     * @param uri uri
     * @return RequestParams
     */
    public RequestParams getDefaultParams(@NonNull String uri) {
        RequestParams params = new RequestParams();
        params.setUri(getUri(uri));

        HttpController.getController().onConfigParams(params);

        Context context = x.app().getApplicationContext();
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            Locale locale = context.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            String str;

            if (language.equals("zh")) {
                str = "zh";
            } else {
                str = "en";
            }
            params.addParameter("language", str);
            params.addParameter("version", String.valueOf(info.versionCode));
            params.addParameter("platform", "android");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return params;
    }
}
