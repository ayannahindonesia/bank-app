package com.ayannah.asira.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import com.ayannah.asira.R;

public class PlafondEditText extends AppCompatEditText {

    private PlafondEdittextListener listener;

    private Paint mTextPaint;

    public PlafondEditText(Context context) {
        super(context);
    }

    public PlafondEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlafondEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //set font
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
        setTypeface(typeface);
        setTextColor(getResources().getColor(R.color.colorAsiraAccent));
    }


    public void setOnHideSoftKeyboardAction(PlafondEdittextListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        listener.setOnHideSoftKeyboard(keyCode, event);

        return false;
    }

    public interface PlafondEdittextListener{

        void setOnHideSoftKeyboard(int keyCode, KeyEvent event);

    }
}
