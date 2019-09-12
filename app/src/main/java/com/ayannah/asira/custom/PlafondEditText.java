package com.ayannah.asira.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

public class PlafondEditText extends AppCompatEditText {

    private PlafondEdittextListener listener;

    public PlafondEditText(Context context) {
        super(context);
    }

    public PlafondEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlafondEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOnHideSoftKeyboardAction(PlafondEdittextListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        listener.setOnHideSoftKeyboard(keyCode, event);
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//
//            InputMethodManager mgr = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            mgr.hideSoftInputFromWindow(this.getWindowToken(), 0);
//
//
//        }

        return false;
    }

    public interface PlafondEdittextListener{

        void setOnHideSoftKeyboard(int keyCode, KeyEvent event);

    }
}
