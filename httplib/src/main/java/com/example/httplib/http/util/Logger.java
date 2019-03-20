package com.example.httplib.http.util;

import android.util.Log;

/**
 * Describe:日志打印
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public final class Logger {

    private static boolean show;
    private static final String TAG = Logger.class.getSimpleName();

    public static void show(boolean show) {
        if (show) {
            Logger.show = true;
            d("show");
        } else {
            d("hide");
            Logger.show = false;
        }
    }

    public static boolean isShow() {
        return show;
    }

    public static void d(String message) {
        if (show)
            Log.d(TAG, message);
    }

    public static void http(String tag, String message) {
        if (show)
            Log.d(TAG, "HTTP: " + tag + ": " + message);
    }

    public static void cache(boolean save, String message) {
        if (show)
            Log.d(TAG, "CACHE[" + (save ? "save" : "load") + "]: " + message);
    }

    public static void url(String message) {
        if (show)
            Log.d(TAG, "URL: " + message);
    }
}
