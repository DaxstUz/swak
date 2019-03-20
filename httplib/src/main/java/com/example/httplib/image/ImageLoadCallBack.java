package com.example.httplib.image;

/**
 * Describe:图片处理状态回调接口
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public interface ImageLoadCallBack {
    void onLoadingStart();

    void onLoadingFailed(Exception e, boolean isFirstResource);

    void onLoadingComplete();
}
