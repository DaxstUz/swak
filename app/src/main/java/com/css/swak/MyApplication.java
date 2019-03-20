package com.css.swak;

import android.app.Application;

import com.css.swak.net.NetUtil;
import com.example.httplib.http.HttpController;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpController.init(this, "url", "0");
        HttpController.setDebug(true);

        NetUtil.initCronetEngine(this);
    }
}
