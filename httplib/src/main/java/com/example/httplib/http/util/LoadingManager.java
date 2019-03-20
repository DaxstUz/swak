package com.example.httplib.http.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.httplib.R;

/**
 * Describe:网络请求加载弹窗管理类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class LoadingManager implements DialogInterface.OnDismissListener {
    private Activity activity;
    private AlertDialog alertDialog;
    private String message = "正在加载中...";
    private boolean cancelable;
    private boolean canceledOnTouchOutside;

    public LoadingManager(Activity activity) {
        this.activity = activity;
    }

    public void cancel() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void show() {
        showMessage(message);
    }

    public void showMessage(CharSequence text) {
        if (alertDialog != null) {
            cancel();
        }

        if (alertDialog == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.loading, null);
            TextView message = view.findViewById(R.id.loading_message);
            message.setText(text);

            alertDialog = new AlertDialog.Builder(activity, R.style.LoadingDialog).create();
            alertDialog.setOnDismissListener(this);
            alertDialog.setView(view);
            alertDialog.setCancelable(cancelable);
            alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            alertDialog.show();
        }
    }

    public boolean isShowing() {
        return alertDialog != null && alertDialog.isShowing();
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        if (alertDialog != null) {
            alertDialog.setCancelable(cancelable);
        }
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        if (alertDialog != null) {
            alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onDismissListener != null){
            onDismissListener.onDismiss(dialog);
        }
    }

    private OnDismissListener onDismissListener;

    public void setOnDismissListener(OnDismissListener listener) {
        onDismissListener = listener;
    }

    public interface OnDismissListener {
        void onDismiss(DialogInterface dialog);
    }
}
