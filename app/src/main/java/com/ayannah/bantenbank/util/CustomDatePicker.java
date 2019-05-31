package com.ayannah.bantenbank.util;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomDatePicker extends DatePickerDialog {

    public CustomDatePicker(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);

    }


}
