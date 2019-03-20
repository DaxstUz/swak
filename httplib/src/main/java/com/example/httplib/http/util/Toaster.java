package com.example.httplib.http.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Describe:自定义Toast工具 可直接调用，不需要实例化
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class Toaster {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    public static void showMessage(final Context act, final String msg) {
        if (act == null) {
            return;
        }
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final Context act, final int msg) {
        if (act == null) {
            return;
        }
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final Context act, final String msg, final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (Toaster.class) {
                            if (toast != null) {
                                toast.setText(msg);
                                toast.setDuration(len);
                            } else {
                                toast = Toast.makeText(act, msg, len);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    public static void showMessage(final Context act, final int msg, final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (Toaster.class) {
                            if (toast != null) {
                                toast.setText(msg);
                                toast.setDuration(len);
                            } else {
                                toast = Toast.makeText(act, msg, len);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }
}
