package com.wordpress.grayfaces.days.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wordpress.grayfaces.days.App.Config;
import com.wordpress.grayfaces.days.App.SQLiteHandler;
import com.wordpress.grayfaces.days.Models.Anniversary;
import com.wordpress.grayfaces.days.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView txtCountDay,txtLeft,txtRight;
    private ProgressBar progressBarNext100d;
    private String TAG = "MainActivity";

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
        progressBarNext100d = (ProgressBar) view.findViewById(R.id.progressbarNext100d);
        initDaysAni();
    }
    private void initDaysAni(){
        final SQLiteHandler handler = new SQLiteHandler(getContext());
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
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    Anniversary aniSet = new Anniversary(0,"Boy","Girl","",date);
                    handler.addAni(aniSet);
                    initDaysAni();
                }
            },year,month,day);
            datePicker.setTitle(getString(R.string.choose_day));
            datePicker.show();
            /*DialogFragment dFragment = new DatePickerFragment();

            // Show the date picker dialog fragment
            dFragment.show(getFragmentManager(), "Date Picker");*/
        }
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
