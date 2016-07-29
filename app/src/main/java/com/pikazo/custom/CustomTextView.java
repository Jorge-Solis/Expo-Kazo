/**
 * Copyright (c) 2014 NFC Data, Inc. All Rights Reserved http://nfcdata.com
 * Developed by: Avantica Technologies.
 */
package com.pikazo.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pikazo.R;


/**
 * Custom Text View
 *
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            init(attrs);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontName = a.getString(R.styleable.CustomTextView_fontName);
            if (fontName != null) {
                try {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(),
                            "fonts/" + fontName);
                    setTypeface(myTypeface);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            a.recycle();
        }
    }

}
