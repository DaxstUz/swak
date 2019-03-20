package com.example.httplib.http;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.httplib.http.config.ErrorStatus;
import com.example.httplib.http.core.BaseResponse;
import com.example.httplib.http.core.HttpCallback;
import com.example.httplib.http.util.Toaster;

import org.xutils.common.Callback;

/**
 * Describe:网络请求回调类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public abstract class ResultCallback<T extends BaseResponse> extends HttpCallback<T> {

    public static final String LOGIN_TIMEOUT = "com.csym.bluervoice.LOGIN_TIMEOUT";
    private Activity activity;

    @Override
    public Activity getActivity() {
        return activity;
    }

    public ResultCallback(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public ResultCallback(Activity activity, String cacheKey) {
        super(activity, cacheKey);
        this.activity = activity;
    }

    @Override
    public void loading(String message) {
        super.loading(message);
    }

    @Override
    public void loading(boolean cancelable, boolean canceledOnTouchOutside) {
        super.loading(cancelable, canceledOnTouchOutside);
    }

    @Override
    public void loading(String message, boolean cancelable, boolean canceledOnTouchOutside) {
        super.loading(message, cancelable, canceledOnTouchOutside);
    }

    /**
     * 开始
     *
     * @return 是否自行处理（默认false，当返回true，则不响应默认事件，如：加载弹窗）
     */
    @Override
    public boolean onResultStart() {
        return false;
    }

    /**
     * 成功
     *
     * @param string json
     * @param result object
     * @return 自行判断是否成功
     */
    @Deprecated
    @Override
    public boolean onResultSuccess(String string, T result, boolean fromCache) {
        String code = HttpController.getController().getResponseSucceedCode();
        if (code == null || code.isEmpty() || code.equals(result.getReCode())) {
            // 返回结果成功
            onResultSuccess(result, fromCache);
            onResultSuccess(result);
            return true;
        } else {
            if (!HttpController.getController().onResponseFailure(getActivity(), result)) {
                // 显示结果信息
                String msg = result.getReMsg();
                if (msg != null && !msg.isEmpty()) {
                    Toaster.showMessage(getContext(), msg);
                }
            }
            // 返回结果失败
            onResultFailure(result);
            return false;
        }
    }

    /**
     * 错误
     *
     * @param throwable throwable
     * @param status    status
     * @return 是否自行处理（默认false，当返回true，则不响应默认事件，如：吐司）
     */
    @Override
    public boolean onResultError(Throwable throwable, ErrorStatus status) {
        return false;
    }

    /**
     * 取消
     *
     * @param e CancelledException
     */
    @Override
    public void onResultCancel(Callback.CancelledException e) {

    }

    /**
     * 结束
     *
     * @param success 是否成功
     */
    @Override
    public void onResultFinished(boolean success) {

    }

    public void onResultSuccess(T result, boolean fromCache) {

    }

    public void onResultSuccess(T result) {

    }

    public void onResultFailure(T result) {
        String errCode = result.getReCode();
        Log.d(getClass().getCanonicalName(), "onResultFailure: 错误码 = " + errCode);
        if ("66".equals(errCode)) {//登录超时
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent().setAction(LOGIN_TIMEOUT));
        }
    }
}
