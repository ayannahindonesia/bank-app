package com.ayannah.asira.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.ayannah.asira.R;

public class PinEntryEditText extends AppCompatEditText {

    float mSpace = 10; //24
    float mCharSize = 0;
    float mNumChars = 6;
    float mLineSpacing = 8;
    float mMaxLenght = 6;

    private float mLineStroke = 5;
    private Paint mLinesPaint;
    private Paint mRectPaint;
    private Paint mTextPaint;

    int[][] mStates = new int[][]{
            new int[]{android.R.attr.state_selected},
            new int[]{android.R.attr.state_focused},
            new int[]{-android.R.attr.state_focused}
    };

    private int[] mColors = new int[]{
            Color.TRANSPARENT, getResources().getColor(R.color.colorAsiraText), getResources().getColor(R.color.colorAsiraPrimary)
    };

    ColorStateList mColorStates = new ColorStateList(mStates, mColors);

    private int getColorForState(int... states){
        return mColorStates.getColorForState(states, Color.GRAY);
    }

    private void updateColorForLines(boolean next, int i){

        if(isFocused()){

            if(getText().length() > i) {
                mLinesPaint.setColor(
                        getColorForState(android.R.attr.state_selected));
            }else {
                mLinesPaint.setColor(
                        getColorForState(android.R.attr.state_focused));
            }

            if(next){
                mLinesPaint.setColor(getColorForState(android.R.attr.state_focused));
            }

        }

    }

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

        //set font
        Typeface typeface = ResourcesCompat.getFont(context, R.font.manrope_bold);
        setTypeface(typeface);
        setTextSize(30);

        float multi = context.getResources().getDisplayMetrics().density;
        mSpace = multi * mSpace-1; //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing;
        mMaxLenght = attrs.getAttributeIntValue("pin_entry", "maxLength", 6);
        mNumChars = mMaxLenght;

        mLineStroke = multi * mLineStroke;
        mLinesPaint = new Paint(getPaint());
        mLinesPaint.setStrokeWidth(mLineStroke);

        mTextPaint = new Paint(getPaint());
        mTextPaint.setColor(getResources().getColor(R.color.colorAsiraText));

        mRectPaint = new Paint(getPaint());
        mRectPaint.setStrokeWidth(mLineStroke);
        mRectPaint.setColor(getResources().getColor(R.color.colorAsiraPrimary));

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

            updateColorForLines(i == textLength, i);

            canvas.drawRoundRect(
                    startX, 0, startX + mCharSize, bottom, 12, 12, mRectPaint);

            canvas.drawLine(
                    startX+10, bottom-25, startX + mCharSize - 10, bottom - 25, mLinesPaint);

            if(getText().length() > i){
                float middle = startX + mCharSize / 2;
                canvas.drawText(text, i, i+1, middle - textWidths[0]/2, bottom - mLineSpacing, mTextPaint);
            }

            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }

        }
    }
}
