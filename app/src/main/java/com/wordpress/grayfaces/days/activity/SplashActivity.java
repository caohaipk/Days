package com.wordpress.grayfaces.days.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Project Days
 * Created by Gray on 10/7/2017.
 */

public class SplashActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(SplashActivity.this);
        boolean isUsedPassword = sharedPreferences.getBoolean("isusepassword", false);
        if (isUsedPassword){
            final String password = sharedPreferences.getString("password","password");
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("PASSWORD")
                    .setCancelable(false);
            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            dialog.setView(input);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String inputPassword = input.getText().toString();
                    if (inputPassword.equals("")){
                        Toast.makeText(SplashActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (inputPassword.equals(password)){
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            SplashActivity.this.finish();
                        } else {
                            Toast.makeText(SplashActivity.this, "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SplashActivity.this.finish();
                }
            });
            dialog.show();
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    }
}
