package com.css.swak;

import android.app.Application;

import com.css.swak.net.NetUtil;

import org.xutils.x;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
//        x.Ext.setDebug(false);

        NetUtil.initCronetEngine(this);
    }
}
