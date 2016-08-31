package com.example.hasee.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by hasee on 2016/8/31.
 */
public class TimePickerFragment extends DialogFragment{
    public static final String EXTRA_TIME = "com.example.hasee.criminalintent.time";
    private Date mTime;

    public static TimePickerFragment getInstanse(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME,date);
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(args);
        return  timePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        mTime =(Date) getArguments().getSerializable(EXTRA_TIME);
        calendar.setTime(mTime);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteOfHour = calendar.get(Calendar.MINUTE);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time,null);
        TimePicker timePicker = (TimePicker) v.findViewById(R.id.crime_time_picker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minuteOfHour);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mTime = new GregorianCalendar(year,month,day,hourOfDay,minute).getTime();
                Log.i("TimePickerFragment",hourOfDay+":"+minute);
                getArguments().putSerializable(EXTRA_TIME,mTime);
            }
        });
        return new TimePickerDialog.Builder(getActivity())
                .setTitle(R.string.time_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment()==null) {
            return;
        }
        Intent data = new Intent();

        data.putExtra(EXTRA_TIME,mTime);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,data);
    }
}
