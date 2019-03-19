package com.css.swak;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class SelfActivity extends AppCompatActivity {


    private VideoView videoView;

    private MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);

        videoView = findViewById(R.id.videoView);

        mediaController = new MediaController(videoView.getContext());


//        videoView.setVideoPath("https://app-share.ieyecloud.com/wx_camera_1538534375994.mp4");
//        videoView.setVideoPath("https://app-share.ieyecloud.com/week01a.mp4");
        videoView.setVideoPath("https://ugcbsy.qq.com/uwMRJfz-r5jAYaQXGdGnC2_ppdhgmrDlPaRvaV7F2Ic/v08119tol3j.mp4?sdtfrom=v5010&guid=4fbe174171ae5a1dddc80196bd1febf2&vkey=BEFACA1A13E483C7130DF25CD3608B31D09D8802DB4B95F8DCC4831F4879E86FCA990114D99AE53E13AFE0CE8359DCA2F1FBDBAE5CAA8A25A0C49232ED27B26DADAD1C49D7CD1E7D94801EEE7D1B0A6EE27281BDA7CDF7F23942019A386090C2B81F68972489D944E2E6297EDE4BF0AB1B77636AC1E1D193&platform=2https://ugcbsy.qq.com/uwMRJfz-r5jAYaQXGdGnC2_ppdhgmrDlPaRvaV7F2Ic/v08119tol3j.mp4?sdtfrom=v5010&guid=4fbe174171ae5a1dddc80196bd1febf2&vkey=BEFACA1A13E483C7130DF25CD3608B31D09D8802DB4B95F8DCC4831F4879E86FCA990114D99AE53E13AFE0CE8359DCA2F1FBDBAE5CAA8A25A0C49232ED27B26DADAD1C49D7CD1E7D94801EEE7D1B0A6EE27281BDA7CDF7F23942019A386090C2B81F68972489D944E2E6297EDE4BF0AB1B77636AC1E1D193&platform=2");
        videoView.setMediaController(mediaController);
//        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

//        videoView.start();

    }
}
