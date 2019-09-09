package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetDialogLogout extends BottomSheetDialogFragment {

    private BottomSheetDialofLogoutListener listener;

    public void setOnClickListener(BottomSheetDialofLogoutListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_logout, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @OnClick(R.id.yes)
    void onClickYes(){

        listener.onClickYes();

    }

    @OnClick(R.id.no)
    void onClickNo(){

        listener.onClickNo();
    }

    public interface  BottomSheetDialofLogoutListener{
        void onClickYes();
        void onClickNo();
    }
}
