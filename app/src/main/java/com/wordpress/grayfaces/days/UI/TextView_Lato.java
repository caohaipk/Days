package com.wordpress.grayfaces.days.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by pcquy on 3/25/2017.
 */

public class  TextView_Lato extends android.support.v7.widget.AppCompatTextView {

    public TextView_Lato(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_Lato(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_Lato(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Medium.ttf");
            setTypeface(tf);
        }
    }
}
