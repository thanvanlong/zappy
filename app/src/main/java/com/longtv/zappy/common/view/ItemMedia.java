package com.longtv.zappy.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class ItemMedia extends RelativeLayout {
    public ItemMedia(Context context) {
        super(context);
    }

    public ItemMedia(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemMedia(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemMedia(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        this.setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);

    }
}
