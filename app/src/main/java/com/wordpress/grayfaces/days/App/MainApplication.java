package com.wordpress.grayfaces.days.App;

import android.app.Application;

import com.wordpress.grayfaces.days.Ulti.TypefaceUtil;

/**
 * Project Days
 * Created by Gray on 8/31/2017.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate()
    {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/SedgwickAve-Regular.ttf");
    }
}
