package com.css.swak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.css.swak.net.MyUrlRequestCallback;
import com.css.swak.net.NetUtil;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoH5Activity extends AppCompatActivity {

    private WebView webview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

//        CustomEventBanner banner;

        webview=findViewById(R.id.webview);
//        webview.loadUrl("https://app-share.ieyecloud.com/wx_camera_1538534375994.mp4");
        webview.loadUrl("https://app-share.ieyecloud.com/week01a.mp4");
//        webview.loadUrl("https://ugcbsy.qq.com/uwMRJfz-r5jAYaQXGdGnC2_ppdhgmrDlPaRvaV7F2Ic/v08119tol3j.mp4?sdtfrom=v5010&guid=4fbe174171ae5a1dddc80196bd1febf2&vkey=BEFACA1A13E483C7130DF25CD3608B31D09D8802DB4B95F8DCC4831F4879E86FCA990114D99AE53E13AFE0CE8359DCA2F1FBDBAE5CAA8A25A0C49232ED27B26DADAD1C49D7CD1E7D94801EEE7D1B0A6EE27281BDA7CDF7F23942019A386090C2B81F68972489D944E2E6297EDE4BF0AB1B77636AC1E1D193&platform=2https://ugcbsy.qq.com/uwMRJfz-r5jAYaQXGdGnC2_ppdhgmrDlPaRvaV7F2Ic/v08119tol3j.mp4?sdtfrom=v5010&guid=4fbe174171ae5a1dddc80196bd1febf2&vkey=BEFACA1A13E483C7130DF25CD3608B31D09D8802DB4B95F8DCC4831F4879E86FCA990114D99AE53E13AFE0CE8359DCA2F1FBDBAE5CAA8A25A0C49232ED27B26DADAD1C49D7CD1E7D94801EEE7D1B0A6EE27281BDA7CDF7F23942019A386090C2B81F68972489D944E2E6297EDE4BF0AB1B77636AC1E1D193&platform=2");

        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        
//        webview.setWebChromeClient(new WebChromeClient());
//        webview.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                view.
//            }
//        });

        getData();

        getData2();
    }

    private void getData() {

       JSONObject param=new JSONObject();
        try {
            param.put("cat","6");
            param.put("appid","android");
            param.put("count","10");
            param.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtil.postReq("https://app.ieyecloud.com/ieye-appserver/news/list",param,new MyUrlRequestCallback(){
            @Override
            public void success(byte[] byteArray) {

                String str=new String(byteArray);
                Log.d("uztag",str);
            }

            @Override
            public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
                super.onFailed(request, info, error);
            }
        });
    }

    private void getData2() {

        JSONObject param=new JSONObject();
        try {
            param.put("cat","6");
            param.put("appid","android");
            param.put("count","10");
            param.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtil.getReq("http://10.0.13.141:8096/clinic-depart?hospitalId=8635",new MyUrlRequestCallback(){
            @Override
            public void success(byte[] byteArray) {

                String str=new String(byteArray);
                Log.d("uztag",str);
            }

            @Override
            public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
                super.onFailed(request, info, error);
            }
        });
    }
}
