package com.css.swak.widget;

import android.content.Context;
import android.widget.MediaController;

public class MyMediaController extends MediaController {


    public MyMediaController(Context context) {
        super(context);
        initFloatingWindow();
    }

    private void initFloatingWindow() {
//        mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//        mWindow = new PhoneWindow(mContext);
//        mWindow.setWindowManager(mWindowManager, null, null);
//        mWindow.requestFeature(Window.FEATURE_NO_TITLE);
//        mDecor = mWindow.getDecorView();
//        mDecor.setOnTouchListener(mTouchListener);
//        mWindow.setContentView(this);
//        mWindow.setBackgroundDrawableResource(android.R.color.transparent);
//
//        // While the media controller is up, the volume control keys should
//        // affect the media stream type
//        mWindow.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
//        requestFocus();


    }
}