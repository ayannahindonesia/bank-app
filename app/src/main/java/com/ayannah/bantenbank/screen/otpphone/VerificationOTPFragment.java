package com.ayannah.bantenbank.screen.otpphone;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.custom.PinEntryEditText;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;
import com.ayannah.bantenbank.screen.success.SuccessActivity;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VerificationOTPFragment extends BaseFragment implements VerificationOTPContract.View {

    @BindView(R.id.secretDummyCode)
    TextView secretDummyCode;

    @BindView(R.id.etPin)
    PinEntryEditText etPin;

    @BindView(R.id.loadingProgress)
    LinearLayout pgLoading;

    @BindView(R.id.successIndicator)
    LinearLayout successIndicator;

    @BindView(R.id.errorIndicator)
    LinearLayout errorIndicator;

    @Inject
    @Named("purpose")
    String purpose;

    @Inject
    @Named("idloan")
    int idLoan;

    @Inject
    VerificationOTPContract.Presenter mPresenter;

    private String REGISTER = "regist";
    private String PINJAMAN = "pinjaman";
    private String RESUBMIT_LOAN = "resubmit_loan";
    private String RESUBMIT_REGIST = "resubmit_regist";

    private AlertDialog dialog;

    @Inject
    public VerificationOTPFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.content_verification_otp;
    }

    @Override
    protected void initView(Bundle state) {

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        //this condition for register purposes
        //we dont use it if pinjaman purposes
        if(purpose.equals(REGISTER)){
            Bundle bundle = parentActivity().getIntent().getExtras();
            assert  bundle!=null;
            mPresenter.getPublicToken(bundle.getString(FormOtherFragment.PHONE), bundle.getString(FormOtherFragment.PASS), "init");
        }


    }

    @OnTextChanged(value = R.id.etPin, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(CharSequence charSequence) {
        if (charSequence.length() == 6) {
//            Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
//            assert bundle != null;

            dialog.show();

            if(purpose.equals(REGISTER)){

                registerNewAccount(charSequence);

            }else if(purpose.equals(PINJAMAN)){

                loanRequest(charSequence);

            }else if(purpose.equals(RESUBMIT_LOAN)){

                resubmitLoanRequest(idLoan);
            } else if (purpose.equals(RESUBMIT_REGIST)) {
                resubmitRegister(charSequence);
            }

        }
    }

    private void resubmitRegister(CharSequence charSequence) {

        String phone = parentActivity().getIntent().getExtras().getString(FormOtherFragment.PHONE);
        String otp = charSequence.toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("otp_code", otp);

        mPresenter.postOTPVerify(jsonObject);
    }

    private void resubmitLoanRequest(int idLoan) {

        mPresenter.resubmitLoanOTP(idLoan, etPin.getText().toString().trim());

    }

    private void registerNewAccount(CharSequence charSequence) {

        String phoneNo = parentActivity().getIntent().getExtras().getString(FormOtherFragment.PHONE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phoneNo);
        jsonObject.addProperty("otp_code", charSequence.toString());

        mPresenter.postOTPVerify(jsonObject);

    }


    private void loanRequest(CharSequence charSequence) {
        String id_loan = parentActivity().getIntent().getStringExtra("id_loan");
        String otp_loan = charSequence.toString();

        JsonObject json = new JsonObject();
        json.addProperty("otp_code", otp_loan);

        mPresenter.postVerifyLoanByOTP(id_loan, json);

    }

    @Override
    public void OTPVerified() {
//        Intent intent = new Intent(parentActivity(), MainMenuActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        parentActivity().startActivity(intent);
//        parentActivity().finish();

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        String phone = bundle.getString(FormOtherFragment.PHONE);
        String pass = bundle.getString(FormOtherFragment.PASS);
//
        mPresenter.getPublicToken(phone, pass, "otp");

//        mPresenter.setUserIdentity();
    }

    @Override
    public void successVerifyLoan() {

        dialog.dismiss();

        Intent intent = new Intent(parentActivity(), SuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(SuccessActivity.SUCCESS_TITLE, "Verifikasi Berhasil!");
        intent.putExtra(SuccessActivity.SUCCESS_DESC, "Pengajuan pinjaman berhasil diverifikasi. Silakan menunggu beberapa saat hingga ada konfirmasi dari bank. Terima kasih.");
        parentActivity().startActivity(intent);
        parentActivity().finish();


    }

    @Override
    public void completeCreateUserToken() {
//        dialog.dismiss();
        mPresenter.setUserIdentity();
    }

    @Override
    public void showErrorMessage(String message) {
        dialog.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginComplete() {
        dialog.dismiss();

        Intent login = new Intent(parentActivity(), MainMenuActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(login);

        parentActivity().finish();
    }
}
