package com.css.swak;

import android.app.Application;

import com.css.swak.net.NetUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetUtil.initCronetEngine(this);
    }
}
