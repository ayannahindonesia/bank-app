package com.ayannah.asira.screen.summary;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.FormDynamic;
import com.ayannah.asira.data.model.Installments;
import com.ayannah.asira.dialog.BottomDialogHandlingError;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.dialog.DialogTableInstallment;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.ayannah.asira.util.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class SummaryTransactionFragment extends BaseFragment implements SummaryTransactionContract.View, Validator.ValidationListener {

    private static final String TAG = SummaryTransactionFragment.class.getSimpleName();

    @Inject
    SummaryTransactionContract.Presenter mPresenter;

    @BindView(R.id.pinjaman)
    TextView tvPinjaman;

    @BindView(R.id.tenor)
    TextView tvTenor;

//    @BindView(R.id.bunga)
//    TextView tvBunga;

    @BindView(R.id.biayaAdmin)
    TextView tvBiayaAdmin;

    @BindView(R.id.jumlahPencairan)
    TextView jumlahPencairan;

    @BindView(R.id.angsuranBulanan)
    TextView tvAngsuran;

    @BindView(R.id.alasan)
    TextView tvAlasan;

    @BindView(R.id.tujuanPinjam)
    TextView tvTujuanPinjam;

    @Checked(message = "Mohon klik untuk menyetujuinya")
    @BindView(R.id.checkDisclaimer)
    CheckBox checkDisclaimer;

    @BindView(R.id.selectedProduct)
    TextView tvSelectedProduct;

    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;

    //value purposes
    @Inject
    @Named("pinjaman")
    int pinjaman;

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

//    @Inject
//    @Named("interest")
//    int interest;

    @Inject
    @Named("admin")
    int admin;

    @Inject
    @Named("layanan")
    int layanan;

    @Inject
    @Named("productid")
    int productid;

    @Inject
    @Named("pencairan")
    double pencairan;

    @Inject
    @Named("borrowerID")
    int borrowerID;

    @Inject
    @Named("bankAccountNumber")
    String bankAccountNumber;

    @Inject
    @Named("installment")
    ArrayList<Installments> installments;

    @Inject
    @Named("form_info")
    ArrayList<FormDynamic> formDynamics;

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

//        double calculateBunga = ((int) pinjaman * interest) / 100;
//        tvBunga.setText(CommonUtils.setRupiahCurrency(interest));

        int calBiayaAdmin = admin;
        tvBiayaAdmin.setText(CommonUtils.setRupiahCurrency(calBiayaAdmin));

        jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int)pencairan));

//        double calAngsuran = (pinjaman + interest + calBiayaAdmin)/tenor;
        if (angsuranBulan == 0) {
            tvAngsuran.setText(Html.fromHtml("<u>Lihat Tabel</u>"));
            tvAngsuran.setClickable(true);
        } else {
            tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.ceil(angsuranBulan)));
            tvAngsuran.setClickable(false);
        }

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

//        checkDisclaimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked){
//
//                    buttonSubmit.setBackgroundResource(R.drawable.button_register);
//
//                }else {
//
//                    buttonSubmit.setBackgroundResource(R.drawable.button_register_disabled);
//
//                }
//
//            }
//        });

    }

    @OnClick(R.id.angsuranBulanan)
    void onClickInstallment() {
        DialogTableInstallment dialog = new DialogTableInstallment();
        dialog.setInstallments(installments);
        dialog.showNow(this.getFragmentManager(), "installmentTable");
    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        //validate before submit
        if(checkDisclaimer.isChecked()) {
            validator.validate();
        }else {
            Toast.makeText(parentActivity(), "Mohon setujui Syarat dan Ketentuan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showErrorMessages(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successLoanApplication(String id_loan) {

//        if (borrowerID != 0) {
//            if (bankAccountNumber.equals("") || bankAccountNumber == null) {
//                BottomSheetDialogGlobal dialogs = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(), BottomSheetDialogGlobal.NO_ACCOUNT_NUMBER_AGENT,
//                        "Nomor Rekening Nasabah Belum Tersedia",
//                        "Nasabah Anda belum bisa mengajukan pinjaman karena belum memiliki nomor rekening pada bank ini.",
//                        R.drawable.no_account_number);
//
//                dialogs.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
//                    @Override
//                    public void onClickButtonDismiss() {
//                        dialogs.dismiss();
//                    }
//
//                    @Override
//                    public void onClickButtonYes() {
//                        dialogs.dismiss();
//                    }
//
//                    @Override
//                    public void closeApps() {
//
//                        dialogs.dismiss();
//
//                    }
//                });
//            } else {
//                mPresenter.requestOTPForLoanAgent(id_loan);
//            }
//        } else {
//            mPresenter.requestOTPForLoan(id_loan);
//        }

        if (borrowerID != 0) {
            mPresenter.requestOTPPersonal(false, 1);
        } else {
            mPresenter.requestOTPPersonal(true, 1);
        }

        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
        if (borrowerID != 0) {
            intent.putExtra("purpose", "post_pinjaman_agent");
        } else {
            intent.putExtra("purpose", "post_pinjaman");
        }

        intent.putExtra("id_loan", id_loan);
        startActivity(intent);
        parentActivity().finish();
//            }

    }

    @Override
    public void successGetOtp(String loanOTP, String id_loan) {

        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
        if (borrowerID != 0) {
            intent.putExtra("purpose", "post_pinjaman_agent");
        } else {
            intent.putExtra("purpose", "post_pinjaman");
        }
//        intent.putExtra("otp_loan", loanOTP);
        intent.putExtra("id_loan", id_loan);
        startActivity(intent);
        parentActivity().finish();
    }

    @Override
    public void cannotMakingLoan() {

        BottomSheetDialogGlobal dialog = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(), BottomSheetDialogGlobal.NO_ACCOUNT_NUMBER,
                "Gagal mengajukan pinjaman",
                "Kamu belum bisa mengajukan pinjaman karena belum memiliki nomor rekening pada bank ini.",
                R.drawable.no_account_number);

        dialog.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {

            }

            @Override
            public void onClickButtonYes() {

            }

            @Override
            public void closeApps() {

                dialog.dismiss();

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        //if validate is success
        JsonObject json = new JsonObject();

        int x = (int) pinjaman;

        if (borrowerID != 0) {
            json.addProperty("borrower", borrowerID);
        }
        json.addProperty("loan_amount", x);
        json.addProperty("installment", tenor);
        json.addProperty("loan_intention", alasan);

        if (tujuan.isEmpty()){
            json.addProperty("intention_details", "-");
        }else {
            json.addProperty("intention_details", tujuan);
        }

        json.addProperty("product", productid);
        json.addProperty("service", layanan);
        json.addProperty("form_info", new Gson().toJson(formDynamics));

        if (borrowerID != 0) {
            mPresenter.postLoanAgent(json);
        } else {
            mPresenter.loanApplication(json);
        }

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

    @Override
    public void errorSendLoan(String message, int code) {

        BottomDialogHandlingError error = new BottomDialogHandlingError(message, code);
        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
        error.setOnClickLister(new BottomDialogHandlingError.BottomDialogHandlingErrorListener() {
            @Override
            public void onClickOk() {
                error.dismiss();
            }
        });

    }
}