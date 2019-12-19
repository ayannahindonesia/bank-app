package com.ayannah.asira.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerCustom extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerCustomListener datePickerCustomListener;

    private DatePickerDialog datePickerDialog;

    public void setOnSelectedDate(DatePickerCustomListener datePickerCustomListener){
        this.datePickerCustomListener = datePickerCustomListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Locale locale = new Locale("in", "ID");
        Locale.setDefault(locale);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR) - 17;
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            final Context themedContext = new ContextThemeWrapper(
                    this.getContext(),
                    android.R.style.Theme_Holo_Light_Dialog
            );

            datePickerDialog = new DatePickerDialogForN(
                    themedContext,
                    this,
                    year,
                    month,
                    day
            );
        } else {
            datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, this, year, month, day);
        }

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("in", "ID"));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        datePickerCustomListener.onSelectedDate(sdf.format(calendar.getTime()));
    }

    public interface DatePickerCustomListener{

        void onSelectedDate(String selectedDate);
    }
}
