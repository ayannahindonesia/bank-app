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

public class BottomSheetAggreement extends BottomSheetDialogFragment {

    private Unbinder mUnbinder;
    private BottomSheetKebijakanListener listener;

    @BindView(R.id.desc)
    TextView desc;

    @BindView(R.id.title)
    TextView title;

    public void setOnCheckListener(BottomSheetKebijakanListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_aggreement, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_confirm)
    void onClick(){

        listener.onClickSetuju();

    }

    @OnClick(R.id.close)
    void onClickClose(){

        listener.onClickClose();

    }

    public interface BottomSheetKebijakanListener{

        void onClickSetuju();

        void onClickClose();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
