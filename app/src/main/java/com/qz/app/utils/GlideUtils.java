package com.qz.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.qz.app.http.API;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by dyc on 2016/12/28.
 */

public class GlideUtils {

    public static void setImage(Context context, String url, int defalutimg, int errorimg, ImageView headimg) {
        if (!TextUtils.isEmpty(url) && !url.contains("http://")) {
            url = API.SERVER_HOST + url;
        }
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(defalutimg)
                .crossFade()
                .error(errorimg)
                .into(headimg);
    }

    public static void setImageURI(Context context, Uri url, int defalutimg, int errorimg, ImageView headimg) {
//        if(!TextUtils.isEmpty(url)&&!url.contains("http://")){
//            url= API.SERVER_HOST+url;
//        }
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(defalutimg)
                .crossFade()
                .error(errorimg)
                .into(headimg);
    }

    public static void setImage(Fragment fragment, String url, int defalutimg, int errorimg, ImageView headimg) {
        if (!TextUtils.isEmpty(url) && !url.contains("http://")) {
            url = API.SERVER_HOST + url;
        }
        Glide.with(fragment)
                .load(url)
                .centerCrop()
                .placeholder(defalutimg)
                .crossFade()
                .error(errorimg)
                .into(headimg);
    }

    public static void setRoundImage(final Context context, String url, int defalutimg, int errorimg, final ImageView headimg) {
        if (!TextUtils.isEmpty(url) && !url.contains("http://")) {
            url = API.SERVER_HOST + url;
        }
        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(defalutimg).error(errorimg).into(new BitmapImageViewTarget(headimg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                headimg.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setLocalRoundImage(final Context context, int resId, int defalutimg, int errorimg, final ImageView headimg) {

        Glide.with(context).load(resId).asBitmap().centerCrop().placeholder(defalutimg).error(errorimg).into(new BitmapImageViewTarget(headimg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                headimg.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setLocalRoundImage2(final Context context, Uri uri, int defalutimg, int errorimg, final ImageView headimg) {

        Glide.with(context).load(uri).asBitmap().centerCrop().placeholder(defalutimg).error(errorimg).into(new BitmapImageViewTarget(headimg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                headimg.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void getRoundCorImg(final Context context, String url, int defalutimg, int errorimg, final ImageView headimg) {
//        Glide.with(context).load(uri).asBitmap().centerCrop().placeholder(defalutimg).error(errorimg).into(new BitmapImageViewTarget(headimg) {
//            @Override
//            protected void setResource(Bitmap resource) {.bitmapTransform(new GlideRoundTransform(context,10))
//        Glide.with(context).load(uri).placeholder(defalutimg).error(errorimg).transform(new GlideRoundTransform(context,50)).into(headimg);
//            }
//        });
        L.v("getRoundCorImg", "___________________________url"+url);
        Glide.with(context).load(url).centerCrop()
                .into(headimg);


    }

    public static void getRoundCorImg(final Context context, Uri uri, int defalutimg, int errorimg, final ImageView headimg) {
//        Glide.with(context).load(uri).asBitmap().centerCrop().placeholder(defalutimg).error(errorimg).into(new BitmapImageViewTarget(headimg) {
//            @Override
//            protected void setResource(Bitmap resource) {.bitmapTransform(new GlideRoundTransform(context,10))
        L.v("getRoundCorImg", "___________________________uri"+uri);
//        Glide.with(context).load(uri).placeholder(defalutimg).error(errorimg).transform(new GlideRoundTransform(context,50)).into(headimg);
        Glide.with(context).load(uri).centerCrop().into(headimg);
//            }
//        });


    }


    public static class GlideRoundTransform extends BitmapTransformation {

        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(200, 200, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }
            int lenth = Math.min(source.getWidth(), source.getHeight());
            lenth = Math.max(lenth, 200);

            Bitmap scaledBitmap = Bitmap.createBitmap(source, 0, 0, lenth, lenth);
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(scaledBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, 200, 200);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }


    //原图，是我博客的头像
//    ImageView image1 = (ImageView) findViewById(R.id.image1);
//    Glide.with(this).load(url).crossFade(1000).into(image1);
//
//    //原图 -> 圆图
//    ImageView image2 = (ImageView) findViewById(R.id.image2);
//    Glide.with(this).load(url).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(image2);
//
//    //原图的毛玻璃、高斯模糊效果
//    ImageView image3 = (ImageView) findViewById(R.id.image3);
//    Glide.with(this).load(url).bitmapTransform(new BlurTransformation(this, 25)).crossFade(1000).into(image3);
//
//    //原图基础上复合变换成圆图 +毛玻璃（高斯模糊）
//    ImageView image4 = (ImageView) findViewById(R.id.image4);
//    Glide.with(this).load(url).bitmapTransform(new BlurTransformation(this, 25), new CropCircleTransformation(this)).crossFade(1000).into(image4);
//
//    //原图处理成圆角，如果是四周都是圆角则是RoundedCornersTransformation.CornerType.ALL
//    ImageView image5 = (ImageView) findViewById(R.id.image5);
//    Glide.with(this).load(url).bitmapTransform(new RoundedCornersTransformation(this, 30, 0, RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(image5);


}
