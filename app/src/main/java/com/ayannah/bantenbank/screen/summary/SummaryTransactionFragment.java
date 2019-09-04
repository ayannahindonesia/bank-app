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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

//    @BindView(R.id.jatuhTempo)
//    TextView jatuhTempo;

    @Checked(message = "Mohon klik untuk menyetujuinya")
    @BindView(R.id.checkDisclaimer)
    CheckBox checkDisclaimer;

    @BindView(R.id.selectedProduct)
    TextView tvSelectedProduct;

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

    @Inject
    @Named("produk")
    String produk;

    @Inject
    @Named("interest")
    int interest;

    @Inject
    @Named("admin")
    int admin;

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

        tvSelectedProduct.setText(produk);

        tvPinjaman.setText(CommonUtils.setRupiahCurrency( (int) pinjaman) );

        tvTenor.setText(String.format("%s bulan", tenor));

        double calculateBunga = ((int) pinjaman * interest) / 100;
        tvBunga.setText(CommonUtils.setRupiahCurrency((int) calculateBunga));

        int calBiayaAdmin = admin;
        tvBiayaAdmin.setText(CommonUtils.setRupiahCurrency(calBiayaAdmin));

        double calAngsuran = (pinjaman + calculateBunga + calBiayaAdmin)/tenor;
        tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(calAngsuran)));

        tvAlasan.setText(alasan);

        if(tujuan == null || tujuan.isEmpty()){
            tvTujuanPinjam.setText("-");
        }else {
            tvTujuanPinjam.setText(tujuan);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, tenor);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
//        jatuhTempo.setText(sdf.format(calendar.getTime()));

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        //validate before submit
        validator.validate();

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
//        intent.putExtra("otp_loan", loanOTP);
        intent.putExtra("id_loan", id_loan);
        startActivity(intent);
        parentActivity().finish();
    }


    @Override
    public void onValidationSucceeded() {

        //if validate is success
        JsonObject json = new JsonObject();

        int x = (int) pinjaman;

        json.addProperty("loan_amount", x);
        json.addProperty("installment", tenor);
        json.addProperty("loan_intention", alasan);

        if (tujuan.isEmpty()){

            json.addProperty("intention_details", "-");
        }else {
            json.addProperty("intention_details", tujuan);
        }



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