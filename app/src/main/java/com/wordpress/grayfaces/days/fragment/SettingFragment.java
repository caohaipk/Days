package com.wordpress.grayfaces.days.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wordpress.grayfaces.days.R;

/**
 * Project Days
 * Created by Gray on 9/12/2017.
 */

public class SettingFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.main_setting_preference);
    }
}
