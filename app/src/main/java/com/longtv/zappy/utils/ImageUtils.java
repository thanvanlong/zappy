package com.longtv.zappy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import jp.wasabeef.blurry.Blurry;

public class ImageUtils {

    public static void loadImageOval(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        Glide.with(context)
                .load(url)
                .apply(options)
                .circleCrop()
                .into(imageView);
    }

    public static void loadImageCorner(Context context, ImageView imageView, String url, int round) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new FitCenter(), new RoundedCorners(round));
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void loadImageCornerBlur(Context context, ImageView imageView, String url, int round) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new FitCenter(), new RoundedCorners(round));
//        Glide.with(context)
//                .load(url)
//                .apply(requestOptions)
//                .into(imageView);

        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)

                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Blurry.with(context).radius(5)
                                .sampling(5)
                                .color(Color.parseColor("#36E6E6E6"))
                                .from(resource).into(imageView);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

}
