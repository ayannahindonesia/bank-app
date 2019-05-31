package com.ayannah.bantenbank.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class PinEntryEditText extends AppCompatEditText {

    float mSpace = 24;
    float mCharSize = 0;
    float mNumChars = 6;
    float mLineSpacing = 8;
    float mMaxLenght = 6;

    private OnClickListener mClickListener;

    public PinEntryEditText(Context context) {
        super(context);
    }

    public PinEntryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinEntryEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs){

        setBackgroundResource(0);

        float multi = context.getResources().getDisplayMetrics().density;
        mSpace = multi * mSpace-1; //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing;
        mMaxLenght = attrs.getAttributeIntValue("pin_entry", "maxLength", 6);
        mNumChars = mMaxLenght;

        //disable copy paste
        super.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        //when tapped, move cursor to end of the text
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(getText().length());
                if(mClickListener != null){
                    mClickListener.onClick(v);
                }
            }
        });

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mClickListener = l;
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        throw new RuntimeException("setCustomSelectionActionModeCallback() not supported");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int availableWidth =
                getWidth() - getPaddingRight() - getPaddingLeft();
        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize =
                    (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }

        int startX = getPaddingLeft();
        int bottom = getHeight() - getPaddingBottom();

        //text width
        Editable text = getText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(getText(), 0, textLength, textWidths);

        for (int i=0; i< mNumChars; i++) {
            canvas.drawLine(
                    startX, bottom, startX + mCharSize, bottom, getPaint());

            if(getText().length() > i){
                float middle = startX + mCharSize / 2;
                canvas.drawText(text, i, i+1, middle - textWidths[0]/2, bottom - mLineSpacing, getPaint());
            }

            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }
        }
    }
}
