package com.css.swak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.css.swak.BaseActivity;
import com.css.swak.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onclick(View view){

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

        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void handlerDo(String str) {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

}

