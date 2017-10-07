package com.wordpress.grayfaces.days.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wordpress.grayfaces.days.R;
import com.wordpress.grayfaces.days.app.Config;
import com.wordpress.grayfaces.days.app.SQLiteHandler;
import com.wordpress.grayfaces.days.dialog.dialogChangeText;
import com.wordpress.grayfaces.days.models.Anniversary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TextView txtCountDay,txtLeft,txtRight,txtTopText,txtBottomText;
    private ProgressBar progressBarNext100d;
    private final String TAG = "MainActivity";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        /*Bundle args = new Bundle();
        args.putString("", param1);
        args.putString("", param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(getActivity());
        boolean syncConnPref = sharedPreferences.getBoolean("isusepassword", false);
        System.out.println("home " +syncConnPref);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Render(view);
        return view;
    }

    private void Render(View view){
        getActivity().getActionBar();
        getActivity().setTitle("Home");
        txtCountDay = (TextView) view.findViewById(R.id.main_txtCountDay);
        txtLeft = (TextView) view.findViewById(R.id.main_txtLeft);
        txtRight = (TextView) view.findViewById(R.id.main_txtRight);
        progressBarNext100d = (ProgressBar) view.findViewById(R.id.main_progressbarNext100d);
        txtTopText = (TextView) view.findViewById(R.id.main_txtTopText);
        txtBottomText = (TextView) view.findViewById(R.id.main_txtBottomText);
        Context context = getActivity();
        initDaysAni(context);
    }
    private void initDaysAni(final Context context){
        final SQLiteHandler handler = new SQLiteHandler(context);
        if (handler.countAni()>0){
            Anniversary ani = handler.getAni(1);
            Date now = Calendar.getInstance().getTime();
            Date aniDate;
            try{
                aniDate =new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(ani.getStartDate());
            } catch (Exception e){
                if (Config.isShowLog){
                    Log.e(TAG, "initDaysAni: "+e.getMessage() );
                }
                aniDate = Calendar.getInstance().getTime();
            }
            long diff = (now.getTime()-aniDate.getTime());
            long countDates = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
            long countLeft,countRight;
            int processPercent;
            if (countDates<99){
                countLeft=0;

            } else {
                countLeft=countDates/100*100;
            }
            countRight=countLeft+100;
            processPercent=(int) countDates%100;
            txtCountDay.setText(String.format("%sd", String.valueOf(countDates)));
            txtLeft.setText(String.format("%sd", String.valueOf(countLeft)));
            txtRight.setText(String.format("%sd", String.valueOf(countRight)));
            progressBarNext100d.setProgress(processPercent);
            txtTopText.setText(String.format("%s",ani.getTopText()));
            txtBottomText.setText(String.format("%s",ani.getBottomText()));
            setViewObjectChange(context,ani.getID());
        } else {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    Anniversary aniSet = new Anniversary(0,"Boy","Girl","",date,"We'd been together","Happy and sad");
                    handler.addAni(aniSet);
                    initDaysAni(context);
                }
            },year,month,day);
            datePicker.setTitle(getString(R.string.choose_day));
            datePicker.show();
            /*DialogFragment dFragment = new DatePickerFragment();

            // Show the date picker dialog fragment
            dFragment.show(getFragmentManager(), "Date Picker");*/
        }
    }
    private void setViewObjectChange(final Context context, final int ID){
        txtTopText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                final dialogChangeText changeText = dialogChangeText.newInstance("TopText", txtTopText.getText().toString());
                changeText.setCancelable(false);
                changeText.setOnDialogFrmCilckListener(new dialogChangeText.OnDialogFragmentClickListener() {
                    @Override
                    public void onOkClicked(dialogChangeText dialog, String content) {
                        SQLiteHandler handler = new SQLiteHandler(context);
                        SQLiteDatabase db = handler.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("TOPTEXT",content);
                        int rowsUpdated = db.update("QL_ANI",values,"ID=?",new String[]{String.valueOf(ID)} );
                        if (rowsUpdated>0){
                            txtTopText.setText(content);
                        }
                        changeText.dismiss();
                    }

                    @Override
                    public void onCancelClicked(dialogChangeText dialog) {
                        changeText.dismiss();
                    }
                });
                changeText.show(fm,"avc");
            }
        });
        txtBottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                final dialogChangeText changeText = dialogChangeText.newInstance("BottomText", txtBottomText.getText().toString());
                changeText.setCancelable(false);
                changeText.setOnDialogFrmCilckListener(new dialogChangeText.OnDialogFragmentClickListener() {
                    @Override
                    public void onOkClicked(dialogChangeText dialog, String content) {
                        SQLiteHandler handler = new SQLiteHandler(context);
                        SQLiteDatabase db = handler.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("BOTTOMTEXT",content);
                        int rowsUpdated = db.update("QL_ANI",values,"ID=?",new String[]{String.valueOf(ID)} );
                        if (rowsUpdated>0){
                            txtBottomText.setText(content);
                        }
                        changeText.dismiss();
                    }

                    @Override
                    public void onCancelClicked(dialogChangeText dialog) {
                        changeText.dismiss();
                    }
                });
                changeText.show(fm,"avc");
            }
        });
    }

}
