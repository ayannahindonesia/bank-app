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
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomDialogHandlingError extends BottomSheetDialogFragment {

    @BindView(R.id.code)
    TextView tvCode;

    @BindView(R.id.message)
    TextView tvMessage;

    private int code;
    private String message;
    private BottomDialogHandlingErrorListener listener;

    private Unbinder mUnbinder;

    public BottomDialogHandlingError(String message, int code){
        this.message = message;
        this.code = code;

    }

    public void setOnClickLister(BottomDialogHandlingErrorListener lister){
        this.listener = lister;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_error_handling, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        if (code == 0) {
            tvCode.setVisibility(View.GONE);
        } else {
            tvCode.setText(String.valueOf(code));
        }

        tvMessage.setText(message);

        return view;
    }

    @OnClick(R.id.btnOk)
    void onClickOk(){

        listener.onClickOk();

    }

    public interface BottomDialogHandlingErrorListener{

        void onClickOk();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
