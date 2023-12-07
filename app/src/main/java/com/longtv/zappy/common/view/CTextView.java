package com.longtv.zappy.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.longtv.zappy.R;


/**
 * Custom Text view
 * Created by neo on 3/24/2016.
 */
public class CTextView extends TextView {
    public CTextView(Context context) {
        super(context);
        initFont(null);
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(attrs);
    }

    public CTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont(attrs);
    }

    /**
     * init font text view
     */
    public void initFont(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CTextView);
        String font = a.getString(R.styleable.CTextView_typeFaceFont);
        String style = a.getString(R.styleable.CTextView_typeFaceStyle);

        if (font != null) {
            if (!style.isEmpty()) {
                font = font + "-" + style;
            }
        }
        a.recycle();
    }

}