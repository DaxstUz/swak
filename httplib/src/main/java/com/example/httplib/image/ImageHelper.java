package com.example.httplib.image;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.httplib.image.config.CircleTransform;
import com.example.httplib.image.config.RoundTransform;

/**
 * Describe:图片处理帮助类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class ImageHelper {

    private volatile static ImageHelper helper;

    public synchronized static ImageHelper getInstance() {
        if (helper == null) {
            synchronized (ImageHelper.class) {
                if (helper == null) {
                    helper = new ImageHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 转换圆形图片
     *
     * @param context context
     * @param radius  if radius > 0 , true is RoundTransform , false is CircleTransform
     * @return BitmapTransformation
     */
    private BitmapTransformation circleTransform(Context context, int radius) {
        return radius > 0 ? new RoundTransform(context, radius) : new CircleTransform(context);
    }

    /**
     * 继续请求
     */
    public void resumeRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 暂停请求
     */
    public void pauseRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除缓存
     */
    public void clear(final Context context) {
        Glide.get(context).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        });
    }

    private DrawableRequestBuilder<String> with(Context context, String url, @DrawableRes int drawableResId, ImageView view, final ImageLoadCallBack callback) {

        DrawableRequestBuilder<String> builder = Glide.with(context.getApplicationContext())
                .load(url)
                .centerCrop()
                // .override(300, 300) // 固定像素
                .dontAnimate() // 去除动画
                .placeholder(drawableResId) // 加载前占位图
                .error(drawableResId);// 加载失败占位图

        if (callback != null) {
            callback.onLoadingStart();
            builder.listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    callback.onLoadingFailed(e, isFirstResource);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    callback.onLoadingComplete();
                    return false;
                }
            });
        }

        return builder;
    }

    /**
     * 加载图片
     *
     * @param context       context
     * @param url           图片地址
     * @param drawableResId 占位图资源
     * @param view          ImageView
     */
    public void load(Context context, String url, @DrawableRes int drawableResId, ImageView view) {
        if (view == null)
            return;
        with(context, url, drawableResId, view, null).into(view);
    }

    /**
     * 加载图片
     *
     * @param context       context
     * @param url           图片地址
     * @param drawableResId 占位图资源
     * @param view          ImageView
     * @param callback      加载回调
     */
    public void load(Context context, String url, @DrawableRes int drawableResId, ImageView view, final ImageLoadCallBack callback) {
        if (view == null)
            return;
        with(context, url, drawableResId, view, callback).into(view);
    }

    /**
     * 加载图片并转换为圆形图片
     *
     * @param context       context
     * @param url           图片地址
     * @param drawableResId 占位图资源
     * @param view          ImageView
     */
    public void loadCircle(Context context, String url, @DrawableRes int drawableResId, ImageView view) {
        loadCircle(context, url, drawableResId, view, -1);
    }

    /**
     * 加载图片并转换为圆角图片
     *
     * @param context       context
     * @param url           图片地址
     * @param drawableResId 占位图资源
     * @param view          ImageView
     * @param radius        圆角半径
     */
    public void loadCircle(Context context, String url, @DrawableRes int drawableResId, ImageView view, int radius) {
        if (view == null)
            return;
        with(context, url, drawableResId, view, null).transform(circleTransform(context.getApplicationContext(), radius)).into(view);
    }

    public void loadCircle(Context context, @DrawableRes int drawableResId, ImageView view) {
        Glide.with(context.getApplicationContext())
                .load(drawableResId)
                .centerCrop()
                .dontAnimate() // 去除动画
                .transform(circleTransform(context.getApplicationContext(), -1))
                .into(view);
    }
}
