package com.ayannah.bantenbank.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ayannah.bantenbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogSuccess extends DialogFragment {

    private DialogSuccessListener listener;

    @BindView(R.id.infoResponse)
    TextView response;

    private String confirm = "";

    public DialogSuccess(){}

    public void setTitleDialog(String title){
        confirm = title;
    }

    public void setOnClick(DialogSuccessListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_success, container, false);
        ButterKnife.bind(this, view);

        response.setText(confirm);

        return view;
    }

    @OnClick(R.id.btnSuccess)
    void onClickButton(){
        listener.onClick();
    }

    public interface DialogSuccessListener{
        void onClick();
    }
}
