package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.util.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomInputPassword extends BottomSheetDialogFragment {

    private DialogInputPassListener listener;

    @BindView(R.id.etPassword)
    EditText etPassword;

    public void setOnClickButtonListener(DialogInputPassListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_input_password, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.yes)
    void onClickYes(){

        listener.onClickYes(etPassword.getText().toString());

    }

    @OnClick(R.id.no)
    void onClickNo(){

        listener.onClickNo();
    }

    public interface DialogInputPassListener {
        void onClickYes(String pass);
        void onClickNo();
    }
}
