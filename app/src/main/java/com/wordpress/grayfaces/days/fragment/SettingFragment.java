package com.wordpress.grayfaces.days.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wordpress.grayfaces.days.R;

/**
 * Project Days
 * Created by Gray on 9/12/2017.
 */

public class SettingFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.main_setting_preference);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.main_setting_preference, false);
    }
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {
        if (key.equals("isusepassword")) {
            boolean isUsedPassword = sharedPreferences.getBoolean(key,false);
            if (isUsedPassword){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("PASSWORD")
                        .setCancelable(false);
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                dialog.setView(input);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();
                        if (password.equals("")){
                            Toast.makeText(getActivity(), "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("password");
                            editor.putString("password",password);
                            editor.commit();
                        }
                    }
                });
                dialog.show();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.commit();
            }
            Preference connectionPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            //connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
