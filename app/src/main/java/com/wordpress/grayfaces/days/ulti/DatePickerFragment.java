package com.wordpress.grayfaces.days.ulti;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                0, this, year, month, day);

        // Create a TextView programmatically.
        EditText tv = new EditText(getActivity());

        // Create a TextView programmatically
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, // Width of TextView
                LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setPadding(10, 10, 10, 10);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setText("This is a custom");
        tv.setTextColor(Color.parseColor("#ff0000"));
        tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));
        /*EditText text = new EditText(getActivity());
        text.setLayoutParams(lp);
        text.setBottom(tv.getBottom());*/
        // Set the newly created TextView as a custom tile of DatePickerDialog
        //dpd.setCustomTitle(tv);

        // Or you can simply set a tile for DatePickerDialog
            /*
                setTitle(CharSequence title)
                    Set the title text for this dialog's window.
            */
        dpd.setTitle("This is a simple title."); // Uncomment this line to activate it

        // Return the DatePickerDialog
        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the chosen date
    }
}