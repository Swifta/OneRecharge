package com.swifta.onerecharge.agent.agentscheduledrecharge;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.swifta.onerecharge.util.IntegerNumberPadder;

import java.util.Calendar;
import java.util.Locale;


public class TimePickerFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    SelectedTimeListener selectedTimeListener;

    public static TimePickerFragment getInstance() {
        return new TimePickerFragment();
    }

    public void setSelectedTimeListener(SelectedTimeListener listener) {
        this.selectedTimeListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),
                this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String hour, minute;

        hour = IntegerNumberPadder.addOneZeroPrefix(i);
        minute = IntegerNumberPadder.addOneZeroPrefix(i1);

        String selectedTime = hour + ":" + minute;

        selectedTimeListener.setTime(selectedTime);
    }

    public interface SelectedTimeListener {
        void setTime(String time);
    }
}
