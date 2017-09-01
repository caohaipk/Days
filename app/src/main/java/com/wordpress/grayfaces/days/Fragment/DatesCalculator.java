package com.wordpress.grayfaces.days.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wordpress.grayfaces.days.App.Config;
import com.wordpress.grayfaces.days.App.SQLiteHandler;
import com.wordpress.grayfaces.days.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatesCalculator.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatesCalculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatesCalculator extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String TAG = "DatesCalculatorFragment";
    private TextView txtStartDate,txtCalDate;
    private EditText txtNumberDates;

    public DatesCalculator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatesCalculator.
     */
    // TODO: Rename and change types and number of parameters
    public static DatesCalculator newInstance(String param1, String param2) {
        DatesCalculator fragment = new DatesCalculator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dates_calculator, container, false);
        Render(view);
        return view;
    }

    private void Render(View view){
        getActivity().getActionBar();
        getActivity().setTitle("Dates Calculator");
        txtStartDate = (TextView) view.findViewById(R.id.cal_txtStartDate);
        txtCalDate = (TextView) view.findViewById(R.id.cal_txtCalDate);
        txtNumberDates = (EditText) view.findViewById(R.id.cal_txtNumberDates);
        initStartDate();
    }

    private void initStartDate(){
        SQLiteHandler handler = new SQLiteHandler(getContext());
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM QL_ANY",null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String startDate = cursor.getString(cursor.getColumnIndex("STARTDATE"));
            txtStartDate.setText(startDate);
            long NumberDates = CalculatorNumberDates(startDate);
            txtNumberDates.setText(String.valueOf(NumberDates));
        } else {
            Date sdate = Calendar.getInstance().getTime();
            String startDate = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(sdate);
            txtStartDate.setText(startDate);
            long NumberDates = CalculatorNumberDates(startDate);
            txtNumberDates.setText(String.valueOf(NumberDates));
        }
        cursor.close();
        db.close();
        String calDate = CountEndDate(txtStartDate.getText().toString(),Integer.parseInt(txtNumberDates.getText().toString()));
        txtCalDate.setText(calDate);
        setChange();
    }
    private long CalculatorNumberDates(String startDate){
        Date now = Calendar.getInstance().getTime();
        Date aniDate;
        try{
            aniDate =new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDate);
        } catch (Exception e){
            if (Config.isShowLog){
                Log.e(TAG, "initStartDate: "+e.getMessage() );
            }
            aniDate = Calendar.getInstance().getTime();
        }
        long diff = (now.getTime()-aniDate.getTime());
        long countDates = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        return countDates;
    }
    private String CountEndDate(String startDate,int numberDates){
        try {
            Date sDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDate);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(sDate);
            calendar.add(Calendar.DATE,numberDates);
            String endDay = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(calendar.getTime());
            return endDay;
        } catch (Exception ignored){}
        return "";
    }
    private void setChange(){
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calDate = CountEndDate(txtStartDate.getText().toString(),Integer.parseInt(txtNumberDates.getText().toString()));
                txtCalDate.setText(calDate);
            }
        });
        txtNumberDates.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtNumberDates.getText().toString().equals("")){
                    txtNumberDates.removeTextChangedListener(this);
                    txtNumberDates.setText("0");
                    txtNumberDates.addTextChangedListener(this);
                }
                String calDate = CountEndDate(txtStartDate.getText().toString(),Integer.parseInt(txtNumberDates.getText().toString()));
                txtCalDate.setText(calDate);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
