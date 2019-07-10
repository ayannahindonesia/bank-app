package com.ayannah.bantenbank.screen.summary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;
import com.ayannah.bantenbank.util.CommonUtils;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class SummaryTransactionFragment extends BaseFragment implements SummaryTransactionContract.View, Validator.ValidationListener {

    @Inject
    SummaryTransactionContract.Presenter mPresenter;

    @BindView(R.id.pinjaman)
    TextView tvPinjaman;

    @BindView(R.id.tenor)
    TextView tvTenor;

    @BindView(R.id.bunga)
    TextView tvBunga;

    @BindView(R.id.biayaAdmin)
    TextView tvBiayaAdmin;

    @BindView(R.id.angsuranBulanan)
    TextView tvAngsuran;

    @BindView(R.id.alasan)
    TextView tvAlasan;

    @BindView(R.id.tujuanPinjam)
    TextView tvTujuanPinjam;

    @Checked(message = "Mohon klik untuk menyetujuinya")
    @BindView(R.id.checkDisclaimer)
    CheckBox checkDisclaimer;

    //value purposes
    @Inject
    @Named("pinjaman")
    double pinjaman;

    @Inject
    @Named("tenor")
    int tenor;

    @Inject
    @Named("angsuran_bulan")
    double angsuranBulan;

    @Inject
    @Named("alasan")
    String alasan;

    @Inject
    @Named("tujuan")
    String tujuan;

    private Validator validator;

    @Inject
    public SummaryTransactionFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_summary_transaction;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void initView(Bundle state) {

        tvPinjaman.setText(CommonUtils.setRupiahCurrency( (int) pinjaman) );

        tvTenor.setText(String.format("%s bulan", tenor));

        double calculateBunga = ((pinjaman * 13)/100 ) / (tenor);
        tvBunga.setText(CommonUtils.setRupiahCurrency((int) calculateBunga));

        double calBiayaAdmin = (pinjaman * 1.5)/100;
        tvBiayaAdmin.setText(CommonUtils.setRupiahCurrency((int) calBiayaAdmin));

        double calAngsuran = (pinjaman / (tenor)) + calculateBunga;
        tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(calAngsuran)));

        tvAlasan.setText(alasan);

        tvTujuanPinjam.setText(tujuan);

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        //validate before submit
        validator.validate();
//        JsonObject json = new JsonObject();
//
//        int x = (int) pinjaman;
//
//        json.addProperty("loan_amount", x);
//        json.addProperty("installment", tenor);
//        json.addProperty("loan_intention", alasan);
//        json.addProperty("intention_details",tujuan);
//
//        Log.d("Summaryyy", String.valueOf(x));
//        Log.d("Summaryyy", String.valueOf(tenor));
//        Log.d("Summaryyy", alasan);
//        Log.d("Summaryyy", tujuan);
//
//
//        mPresenter.loanApplication(json);


//        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
//        intent.putExtra("purpose", "pinjaman");
//        intent.putExtra("otp_loan", "");
//        startActivity(intent);

    }

    @Override
    public void showErrorMessages(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successLoanApplication(String id_loan) {

        mPresenter.requestOTPForLoan(id_loan);

    }

    @Override
    public void successGetOtp(String loanOTP, String id_loan) {

        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
        intent.putExtra("purpose", "pinjaman");
        intent.putExtra("otp_loan", loanOTP);
        intent.putExtra("id_loan", id_loan);
        startActivity(intent);
    }


    @Override
    public void onValidationSucceeded() {

        //if validate is success
        JsonObject json = new JsonObject();

        int x = (int) pinjaman;

        json.addProperty("loan_amount", x);
        json.addProperty("installment", tenor);
        json.addProperty("loan_intention", alasan);
        json.addProperty("intention_details",tujuan);



       mPresenter.loanApplication(json);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        //auto call this if there is show up error

        for(ValidationError val: errors){

            View view = val.getView();
            String message = val.getCollatedErrorMessage(parentActivity());

            //display error messages above component checkbox or toast message
            if(view instanceof CheckBox){

                ((CheckBox) view).setError(message);
                Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}