package com.longtv.zappy.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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

}
