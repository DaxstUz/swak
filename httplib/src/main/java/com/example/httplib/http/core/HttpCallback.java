package com.example.httplib.http.core;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.example.httplib.db.cahce.CacheDto;
import com.example.httplib.db.cahce.CacheManager;
import com.example.httplib.http.HttpController;
import com.example.httplib.http.JSONException;
import com.example.httplib.http.config.ErrorStatus;
import com.example.httplib.http.util.GenericsResolver;
import com.example.httplib.http.util.LoadingManager;
import com.example.httplib.http.util.Logger;
import com.example.httplib.http.util.Toaster;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Describe:网络请求回调处理基类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public abstract class HttpCallback<T> implements Callback.ProgressCallback<String> {

    private Context context;
    private Activity activity;

    private Class<?> genericsClass;

    private boolean success;
    private boolean cancel;
    private boolean cache;

    private RequestParams requestParams;
    private Callback.Cancelable callbackCancelable;

    private String cacheKey;

    private LoadingManager loadingManager;
    private String loadingMessage;
    private boolean loadingCancelable;
    private boolean loadingCanceledOnTouchOutside;

    private Gson mGson = new Gson();

    public HttpCallback(Activity activity) {
        this(activity, null);
        this.cache = false;
    }

    /**
     * 缓存构造函数
     *
     * @param activity activity
     * @param cacheKey 缓存Key（必填）
     */
    public HttpCallback(Activity activity, String cacheKey) {
        this.activity = activity;
        this.context = x.app().getApplicationContext();
        // 解析泛型类型
        this.genericsClass = GenericsResolver.getObjectClass(getClass(), HttpCallback.class);
        // 处理缓存
        if (cacheKey != null) {
            this.cache = true;
            this.cacheKey = cacheKey;
            // 加载缓存
            loadCache(cacheKey);
        }
    }

    public Context getContext() {
        return this.context;
    }

    public Activity getActivity() {
        return activity;
    }

    private Class<?> getGenericsClass() {
        return this.genericsClass;
    }

    void setCallbackCancelable(Callback.Cancelable cancelable) {
        this.callbackCancelable = cancelable;
    }

    void setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
        if (cache) {
            if (requestParams != null) {
                // 加载缓存，使用Uri为key
                this.cacheKey = requestParams.getUri();
                loadCache(this.cacheKey);
            } else {
                cache = false;
            }
        }
    }

    private Callback.Cancelable getCallbackCancelable() {
        return callbackCancelable;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    /**
     * 加载缓存
     *
     * @param cacheKey cacheKey
     */
    private void loadCache(String cacheKey) {
        Class<?> cls = getGenericsClass();
        if (cacheKey != null) {
            this.cache = false;
            this.cacheKey = cacheKey;
            try {
                CacheDto cacheInfo = CacheManager.getInstance(getContext()).getCacheByKey(cacheKey);
                if (null != cacheInfo) {
                    String value = cacheInfo.getValue();
                    if (null != value && !value.isEmpty()) {
                        T result = (T) mGson.fromJson(value, cls);
                        if (result != null) {
                            Logger.cache(false, "[" + cls.getSimpleName() + "] " + result);
                            onResultSuccess(value, result,true);
                        }
                    }
                }
            } catch (Exception e) {
                Logger.cache(false, e.getMessage());
            }
        }
    }

    /**
     * 设置加载弹窗信息
     *
     * @param message 弹窗信息
     */
    public void loading(String message) {
        loading(message, this.loadingCancelable, this.loadingCanceledOnTouchOutside);
    }

    /**
     * 设置加载弹窗是否开启隐藏
     *
     * @param cancelable             是否开启返回键隐藏
     * @param canceledOnTouchOutside 是否点击弹窗外部隐藏
     */
    public void loading(boolean cancelable, boolean canceledOnTouchOutside) {
        loading(null, cancelable, canceledOnTouchOutside);
    }

    /**
     * 设置加载弹窗属性
     *
     * @param message                弹窗信息
     * @param cancelable             是否开启返回键隐藏
     * @param canceledOnTouchOutside 是否点击弹窗外部隐藏
     */
    public void loading(String message, boolean cancelable, boolean canceledOnTouchOutside) {
        if (message != null) {
            this.loadingMessage = message;
        }
        this.loadingCancelable = cancelable;
        this.loadingCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    /**
     * 显示加载弹窗
     */
    private void showLoading() {
        if (getActivity() == null && !getActivity().isFinishing()) {
            return;
        }
        if (loadingManager == null) {
            loadingManager = new LoadingManager(getActivity());
            loadingManager.setOnDismissListener(new LoadingManager.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Cancelable cancelable = getCallbackCancelable();
                    if (cancelable != null) {
                        cancelable.cancel();
                    }
                }
            });
        }
        if (this.loadingMessage != null) {
            loadingManager.setMessage(this.loadingMessage);
        }
        loadingManager.setCancelable(this.loadingCancelable);
        loadingManager.setCanceledOnTouchOutside(this.loadingCanceledOnTouchOutside);
        loadingManager.show();
    }

    /**
     * 隐藏加载弹窗
     */
    public void dismissLoading() {
        if (loadingManager != null) {
            loadingManager.cancel();
        }
    }

    @Override
    public void onWaiting() {
        this.success = false;
        this.cancel = false;
    }

    @Deprecated
    @Override
    public void onStarted() {
        Logger.http("Request params", getRequestParams() != null ? getRequestParams().toString() : "NULL");
        this.success = false;
        this.cancel = false;
        // 是否自行处理（返回true，则不显示加载弹窗）
        if (!onResultStart()) {
            showLoading();
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Deprecated
    @Override
    public void onSuccess(String result) {
        Class<?> cls = getGenericsClass();
        Logger.http("Parse result", "[" + (cls != null ? cls.getSimpleName() : "NULL") + "] " + (result != null ? result : "NULL"));
        if (cls == null) {
            throw new IllegalArgumentException("callback generics is null!");
        } else if (result == null || result.isEmpty()) {
            throw new JSONException("Response the result does not exist!");
        } else {
            T t = (T) mGson.fromJson(result, cls);
            if (t == null) {
                throw new JSONException("Parse the response result is empty!");
            } else {
                // 自行判断是否成功
                this.success = onResultSuccess(result, t,false);
                if (this.success && getContext() != null && this.cacheKey != null && !this.cacheKey.isEmpty()
                        && CacheManager.getInstance(getContext()).saveCache(result, this.cacheKey)) {
                    Logger.cache(true, "[" + cls.getSimpleName() + "] " + result);
                }
            }
        }
    }

    @Deprecated
    @Override
    public void onError(Throwable throwable, boolean isOnCallback) {
        this.success = false;
        throwable.printStackTrace();
        ErrorStatus errorStatus;
        if (throwable instanceof SocketTimeoutException) {
            // 连接超时
            errorStatus = ErrorStatus.HTTP_TIMEOUT;
        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            // 无网络
            errorStatus = ErrorStatus.HTTP_UNCONNECTED;
        } else if (throwable instanceof HttpException) {
            // 网络异常
            errorStatus = ErrorStatus.HTTP_EXCEPTION.setErrorCode(((HttpException) throwable).getErrorCode());
        } else if (throwable instanceof JSONException) {
            // 数据异常
            errorStatus = ErrorStatus.DATA_EXCEPTION;
        } else {
            // 其他错误
            errorStatus = ErrorStatus.OTHER_EXCEPTION;
        }

        if (!onResultError(throwable, errorStatus) || !HttpController.getController().onResponseError(throwable, errorStatus)) {
            Toaster.showMessage(getContext(), errorStatus.getMessage());
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        this.cancel = true;
        this.success = false;
        onResultCancel(cex);
    }

    @Deprecated
    @Override
    public void onFinished() {
        if (!this.cancel) {
            // 取消时，不调用
            onResultFinished(this.success);
        }
        this.cancel = false;
        this.success = false;
        dismissLoading();
    }

    /**
     * 开始
     *
     * @return 是否自行处理（当返回true，则不响应默认事件，如：加载弹窗）
     */
    public abstract boolean onResultStart();

    /**
     * 成功
     *
     * @param string json
     * @param result object
     * @return 自行判断是否成功
     */
    public abstract boolean onResultSuccess(String string, T result, boolean fromCache);

    /**
     * 错误
     *
     * @param throwable throwable
     * @param status    status
     * @return 是否自行处理（当返回true，则不响应默认事件，如：吐司）
     */
    public abstract boolean onResultError(Throwable throwable, ErrorStatus status);

    /**
     * 取消
     *
     * @param e CancelledException
     */
    public abstract void onResultCancel(CancelledException e);

    /**
     * 结束
     *
     * @param success 是否成功
     */
    public abstract void onResultFinished(boolean success);
}