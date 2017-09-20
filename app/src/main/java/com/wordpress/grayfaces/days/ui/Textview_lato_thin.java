package com.wordpress.grayfaces.days.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Project Days
 * Created by pcquy on 3/25/2017.
 */

public class Textview_lato_thin extends android.support.v7.widget.AppCompatTextView {

    public Textview_lato_thin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Textview_lato_thin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Textview_lato_thin(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
            setTypeface(tf);
        }
    }
}