package com.ayannah.bantenbank.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.util.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomChangingIncome extends BottomSheetDialogFragment {

    @BindView(R.id.currentSalary)
    TextView currentSalary;

    @BindView(R.id.anotherIncome)
    TextView anotherIncome;

    @BindView(R.id.otherSourceIncome)
    TextView otherSourceIncome;

    @OnClick(R.id.no)
    void onClickNo(){
        listener.onClickNo();
    }

    @OnClick(R.id.yes)
    void onClickYes(){

        listener.onClickYes();
    }

    private BottomSheetChangingIncomeListener listener;

    public BottomChangingIncome(){

    }

    public void setListenerBottomChangingIncome(BottomSheetChangingIncomeListener listener ){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_changingincome, container, false);
        ButterKnife.bind(this, view);

        currentSalary.setText(CommonUtils.setRupiahCurrency(listener.getCurrentSalary()));

        anotherIncome.setText(CommonUtils.setRupiahCurrency(listener.getAnotherIncome()));

        return view;
    }

    public interface BottomSheetChangingIncomeListener{
        void onClickNo();
        void onClickYes();
        int getCurrentSalary();
        int getAnotherIncome();
    }
}
