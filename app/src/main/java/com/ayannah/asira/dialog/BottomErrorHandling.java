package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomErrorHandling extends BottomSheetDialogFragment {

    @BindView(R.id.code)
    TextView tvCode;

    @BindView(R.id.message)
    TextView tvMessage;

    private BottomSheetErrorListener listener;

    private String message;
    private int code;

    private Unbinder mUnbinder;

    public BottomErrorHandling(String message, int code){

        this.message = message;
        this.code = code;

    }

    public void setOnClickListener(BottomSheetErrorListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_error_handling, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        tvCode.setText(String.valueOf(code));

        tvMessage.setText(message);

        return view;
    }

    @OnClick(R.id.btnClose)
    void onClick(){

        listener.onClickClose(code);

    }

    public interface BottomSheetErrorListener{

        void onClickClose(int code);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}