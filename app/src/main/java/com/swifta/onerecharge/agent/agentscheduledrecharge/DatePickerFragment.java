package com.swifta.onerecharge.agent.agentscheduledrecharge;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.swifta.onerecharge.util.IntegerNumberPadder;

import java.util.Calendar;
import java.util.Locale;


public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private SelectedDateListener selectedDateListener;

    public static DatePickerFragment getInstance() {
        return new DatePickerFragment();
    }

    public void setSelectedDateListener(SelectedDateListener selectedDateListener) {
        this.selectedDateListener = selectedDateListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String month, day;

        month = IntegerNumberPadder.addOneZeroPrefix(++i1);
        day = IntegerNumberPadder.addOneZeroPrefix(i2);

        String date = month + "-" + day + "-" + i;

        selectedDateListener.setDate(date);
    }

    public interface SelectedDateListener {
        void setDate(String date);
    }
}
