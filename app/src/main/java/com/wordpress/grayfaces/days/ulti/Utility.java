package com.wordpress.grayfaces.days.ulti;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

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
}
