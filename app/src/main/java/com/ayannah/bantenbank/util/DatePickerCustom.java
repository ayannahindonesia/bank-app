package com.ayannah.bantenbank.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerCustom extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerCustomListener datePickerCustomListener;

    public void setOnSelectedDate(DatePickerCustomListener datePickerCustomListener){
        this.datePickerCustomListener = datePickerCustomListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        datePickerCustomListener.onSelectedDate(sdf.format(calendar.getTime()));
    }

    public interface DatePickerCustomListener{

        void onSelectedDate(String selectedDate);
    }
}
