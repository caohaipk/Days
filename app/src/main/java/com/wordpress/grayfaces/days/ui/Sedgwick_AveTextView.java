package com.wordpress.grayfaces.days.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Sedgwick_AveTextView extends android.support.v7.widget.AppCompatTextView {

	private static Typeface sSedgwick_Ave;

	public Sedgwick_AveTextView(Context context) {
		super(context);
		if (isInEditMode()) return; //Won't work in Eclipse graphical layout
		setTypeface();
	}

	public Sedgwick_AveTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) return;
		setTypeface();
	}

	public Sedgwick_AveTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode()) return;
		setTypeface();
	}
	
	private void setTypeface() {
		if (sSedgwick_Ave == null) {
			sSedgwick_Ave = Typeface.createFromAsset(getContext().getAssets(), "fonts/SedgwickAve-Regular.ttf");
		}
		setTypeface(sSedgwick_Ave);
	}
}
