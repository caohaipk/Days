package com.wordpress.grayfaces.days.ulti;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;

/**
 * Project Days
 * Created by Gray on 9/1/2017.
 */

public class Utility {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void setupUItoHideSoftKeyboard(View rootView, Activity activity) {
        final WeakReference<Activity> wActivity = new WeakReference<Activity>(activity);
        final WeakReference<View> wView = new WeakReference<View>(rootView);
        if (wActivity.get()!=null && wView.get()!=null) {
            // Set up touch listener for non-text box views to hide keyboard.
            if (!(wView.get() instanceof EditText)) {
                wView.get().setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager inputMethodManager = (InputMethodManager) wActivity.get().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(wView.get().getWindowToken(), 0);
                        return false;
                    }
                });
            }

            //If a layout container, iterate over children and seed recursion.
            if (wView.get() instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) wView.get()).getChildCount(); i++) {
                    View innerView = ((ViewGroup) wView.get()).getChildAt(i);
                    setupUItoHideSoftKeyboard(innerView,wActivity.get());
                }
            }
        }
    }
}
