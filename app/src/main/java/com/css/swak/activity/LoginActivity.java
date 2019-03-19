package com.css.swak.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.css.swak.BaseActivity;
import com.css.swak.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * A login screen that offers login via email/password.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.tv_layer)
    private TextView tv_layer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_layer.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_layer.getPaint().setAntiAlias(true);//抗锯齿
    }

    public void onclick(View view) {

//        switch (view.getId()){
//            case R.id.ll_login:
//                NetUtil.postReq("", new JSONObject(), new MyUrlRequestCallback() {
//                    @Override
//                    public void success(byte[] byteArray) {
//                        handlerDo(new String(byteArray));
//                    }
//                });
//                break;
//        }

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void handlerDo(String str) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}

