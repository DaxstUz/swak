package com.example.httplib.image.config;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * Describe:Glide 转换 圆角图
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class RoundTransform extends BitmapTransformation {
    private float mRadius;

    public RoundTransform(Context context, int radius) {
        super(context);
        mRadius = Resources.getSystem().getDisplayMetrics().density * radius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        final Bitmap toReuse = pool.get(outWidth, outHeight, toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
        Bitmap transformed = TransformationUtils.centerCrop(toReuse, toTransform, outWidth, outHeight);

        if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
            toReuse.recycle();
        }

        Bitmap result = pool.get(transformed.getWidth(), transformed.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(transformed.getWidth(), transformed.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(transformed, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));

        RectF rectF = new RectF(0f, 0f, transformed.getWidth(), transformed.getHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);

        if (!pool.put(transformed)) {
            transformed.recycle();
        }

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(mRadius);
    }
}
