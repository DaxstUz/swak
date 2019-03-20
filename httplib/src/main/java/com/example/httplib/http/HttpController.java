package com.example.httplib.http;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.httplib.http.util.Logger;

import org.xutils.x;

/**
 * Describe:网络请求控制类，用来初始化、基础配置
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public final class HttpController {

    public static void init(@NonNull Application application, @NonNull String baseUrl) {
        HttpController.init(application, baseUrl, null);
    }

    public static void init(@NonNull Application application, @NonNull String baseUrl, String succeedCode) {
        x.Ext.init(application);
        HttpController.getController().setBaseUrl(baseUrl);
        HttpController.getController().setResponseSucceedCode(succeedCode);
    }

    public static Context getApplicationContext() {
        return x.app().getApplicationContext();
    }

    public static void setDebug(boolean debug) {
        x.Ext.setDebug(debug);
        Logger.show(debug);
    }

    public static boolean isDebug() {
        return Logger.isShow();
    }

    public static HttpControllerImpl getController() {
        return HttpControllerImpl.getInstance();
    }
}
