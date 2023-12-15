package com.longtv.zappy.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;

import com.longtv.zappy.R;


/**
 * Created by ThanhTD on 3/30/2016.
 */
public class ExpandableTextView extends CTextView {
    private static final int DEFAULT_TRIM_LENGTH = 150;
    public static String ELLIPSIS = "...<font color='#E72432'>Xem thêm</font>";
    public static String COLLAPSE = "   <font color='#E72432'>Rút gọn</font>";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = false;
    private int trimLength;
    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        ELLIPSIS = "...<font color='#E72432'>" + "Xem thêm"+ "</font>";
        COLLAPSE = "   <font color='#E72432'>" + "Rút gọn" + "</font>";
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = !trim;
                setText();
                requestFocusFromTouch();
            }
        });
    }

    private void setText() {
        if (trim) {
            super.setText(Html.fromHtml(getDisplayableText().toString()), bufferType);
            setBackground(getResources().getDrawable(R.drawable.bg_ripple));
        } else if (originalText != null && originalText.length() > trimLength) {
            super.setText(Html.fromHtml(getDisplayableText().toString() + COLLAPSE), bufferType);
            setBackground(getResources().getDrawable(R.drawable.bg_ripple));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }, 200);


    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }
}