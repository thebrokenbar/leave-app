package com.meterohead.leave.leavedetails;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.meterohead.leave.utils.DateConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Nullable;

@FragmentWithArgs
public class DateSetFragment extends DialogFragment {

    public static final String DATE_PARAM = "DATE_PARAM";

    @Nullable
    @Arg(required = false)
    Date date;

    public DateSetFragment() {
        // Required empty public constructor
    }

    public static DateSetFragment newInstance(Fragment targetFragment, @Nullable Date date, int requestCode) {
        DateSetFragmentBuilder builder = new DateSetFragmentBuilder();
        if(date != null) {
            builder.date(date);
        }
        DateSetFragment fragment = builder.build();
        fragment.setTargetFragment(targetFragment, requestCode);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c;
        if(date != null){
            c = DateConverter.getAsCalendar(date);
        } else {
            c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        }
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Intent intent = new Intent();
                date = DateConverter.getDate(year, month, day);
                intent.putExtra(DATE_PARAM, date);
                getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);
            }
        }, y, m, d);
    }
}
