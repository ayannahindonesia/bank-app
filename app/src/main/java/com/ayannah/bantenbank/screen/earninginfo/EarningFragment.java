package com.ayannah.bantenbank.screen.earninginfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.dialog.BottomChangingIncome;
import com.ayannah.bantenbank.screen.loan.LoanActivity;
import com.ayannah.bantenbank.util.CommonUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EarningFragment extends BaseFragment implements EarningContract.View,
        BottomChangingIncome.BottomSheetChangingIncomeListener {

    @Inject
    EarningContract.Presenter mPresenter;

    @BindView(R.id.penghasilam)
    TextView penghasilam;

    @BindView(R.id.seekbarPenghasilan)
    SeekBar seekBar;

    @BindView(R.id.etPendapatanLain)
    EditText etPendapatanLain;
    private String originalStringPendapatanLain;

    @BindView(R.id.etSumberPendapatanLain)
    EditText etSumberPendapatanLain;
    private String originalStringSumberPendapatan;

    private BottomChangingIncome popUpChangingIncome;

    int currentSalary = 25000000;
    int anotherIncome = 10000000;

    @Inject
    public EarningFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_earning;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void initView(Bundle state) {

        setUpPopUp();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if(progress > 0){
                    int x = progress * 1000000;

                    penghasilam.setText(CommonUtils.setRupiahCurrency(x));

                }else {

                    penghasilam.setText(getResources().getString(R.string.default_currency_amount));

                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        etPendapatanLain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etPendapatanLain.removeTextChangedListener(this);

                try {

                    originalStringPendapatanLain = s.toString();

                    long longval;
                    if(originalStringPendapatanLain.contains(",")){
                        originalStringPendapatanLain = originalStringPendapatanLain.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalStringPendapatanLain);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to edittext
                    etPendapatanLain.setText(formattedString);
                    etPendapatanLain.setSelection(etPendapatanLain.getText().length());

                } catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }

                etPendapatanLain.addTextChangedListener(this);

            }
        });

        etSumberPendapatanLain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etSumberPendapatanLain.removeTextChangedListener(this);

                try {

                    originalStringSumberPendapatan = s.toString();

                    long longval;
                    if(originalStringSumberPendapatan.contains(",")){
                        originalStringSumberPendapatan = originalStringSumberPendapatan.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalStringSumberPendapatan);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to edittext
                    etSumberPendapatanLain.setText(formattedString);
                    etSumberPendapatanLain.setSelection(etSumberPendapatanLain.getText().length());

                } catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }

                etSumberPendapatanLain.addTextChangedListener(this);

            }
        });

    }

    private void setUpPopUp() {
        popUpChangingIncome = new BottomChangingIncome();
        popUpChangingIncome.showNow(getChildFragmentManager(), "pop up");
        popUpChangingIncome.setListenerBottomChangingIncome(this);
    }

    @OnClick(R.id.buttonNext)
    void onClickProcess(){

        Intent intent = new Intent(parentActivity(), LoanActivity.class);
        startActivity(intent);

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void onClickNo() {

        Intent pinjaman = new Intent(parentActivity(), LoanActivity.class);
        popUpChangingIncome.dismiss();
        startActivity(pinjaman);

    }

    @Override
    public void onClickYes() {
        popUpChangingIncome.dismiss();

    }

    @Override
    public int getCurrentSalary() {
        return currentSalary;
    }

    @Override
    public int getAnotherIncome() {
        return anotherIncome;
    }
}
