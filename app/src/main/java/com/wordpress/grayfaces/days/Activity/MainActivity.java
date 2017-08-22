package com.wordpress.grayfaces.days.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.grayfaces.days.App.Config;
import com.wordpress.grayfaces.days.App.SQLiteHandler;
import com.wordpress.grayfaces.days.Fragment.HomeFragment;
import com.wordpress.grayfaces.days.Models.Anniversary;
import com.wordpress.grayfaces.days.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtCountDay,txtLeft,txtRight;
    private ProgressBar progressBarNext100d;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_camera:
                        Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_gallery:
                        Toast.makeText(MainActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_slideshow:
                        Toast.makeText(MainActivity.this, "Slidershow", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_manage:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        //Render();
        createView();
    }
    private void createView(){
        txtCountDay = (TextView) findViewById(R.id.main_txtCountDay);
        txtLeft = (TextView) findViewById(R.id.main_txtLeft);
        txtRight = (TextView) findViewById(R.id.main_txtRight);
        progressBarNext100d = (ProgressBar) findViewById(R.id.progressbarNext100d);
        initDaysAni();
    }
    private void initDaysAni(){
        final SQLiteHandler handler = new SQLiteHandler(MainActivity.this);
        if (handler.countAni()>0){
            Anniversary ani = handler.getAni(1);
            Date now = Calendar.getInstance().getTime();
            Date aniDate;
            try{
                aniDate =new SimpleDateFormat("dd/MM/yyyy").parse(ani.getStartDate());
            } catch (Exception e){
                if (Config.isShowLog){
                    Log.e(TAG, "initDaysAni: "+e.getMessage() );
                }
                aniDate = Calendar.getInstance().getTime();
            }
            long diff = (now.getTime()-aniDate.getTime());
            long countDates = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
            long countLeft,countRight;
            if (countDates<99){
                countLeft=0;

            } else {
                countLeft=countDates/100*100;
            }
            countRight=countLeft+100;
            txtCountDay.setText(String.format("%sd", String.valueOf(countDates)));
            txtLeft.setText(String.format("%sd", String.valueOf(countLeft)));
            txtRight.setText(String.format("%sd", String.valueOf(countRight)));
            progressBarNext100d.setProgress((int)(countRight-countDates));
        } else {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(year);
                    Anniversary aniSet = new Anniversary(0,"Boy","Girl","",date);
                    handler.addAni(aniSet);
                    initDaysAni();
                }
            },year,month,day);
            datePicker.setTitle("Chọn ngày...");
            datePicker.show();
            /*DialogFragment dFragment = new DatePickerFragment();

            // Show the date picker dialog fragment
            dFragment.show(getFragmentManager(), "Date Picker");*/
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void Render(){
        LoadHome();
    }

    public  void  LoadHome(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        HomeFragment fragdashboardPG = new HomeFragment();
        fragmentTransaction.replace(R.id.content_main, fragdashboardPG, "fragDashboardPG");
        fragmentTransaction.commitAllowingStateLoss();
        this.setTitle("");
    }
}
