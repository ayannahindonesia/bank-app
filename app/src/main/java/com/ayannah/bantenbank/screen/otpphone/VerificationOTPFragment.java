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
    String purpose;

    @Inject
    VerificationOTPContract.Presenter mPresenter;

    private String REGISTER = "regist";
    private String PINJAMAN = "pinjaman";

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


    }

    @OnTextChanged(value = R.id.etPin, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(CharSequence charSequence) {
        if (charSequence.length() == 6) {
//            Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
//            assert bundle != null;

            if(purpose.equals(REGISTER)){

                registerNewAccount(charSequence);

            }else if(purpose.equals(PINJAMAN)){

                loanRequest();
            }

        }
    }

    private void registerNewAccount(CharSequence charSequence) {

        String phoneNo = parentActivity().getIntent().getExtras().getString("phone");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phoneNo);
        jsonObject.addProperty("otp_code", charSequence.toString());

        mPresenter.postOTPVerify(jsonObject);

    }


    private void loanRequest() {
        String id_loan = parentActivity().getIntent().getStringExtra("id_loan");
        String otp_loan = parentActivity().getIntent().getStringExtra("otp_loan");

        JsonObject json = new JsonObject();
        json.addProperty("otp_code", otp_loan);

        mPresenter.postVerifyLoanByOTP(id_loan, json);

    }

    @Override
    public void OTPVerified() {
        Intent intent = new Intent(parentActivity(), MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        parentActivity().startActivity(intent);
        parentActivity().finish();
    }

    @Override
    public void successVerifyLoan() {

        Log.i("Success Verify Loan", "Pinjaman berhasil di Verify");

        Intent intent = new Intent(parentActivity(), SuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(SuccessActivity.SUCCESS_TITLE, "Verifikasi Berhasil!");
        intent.putExtra(SuccessActivity.SUCCESS_DESC, "Pengajuan pinjaman berhasil diverifikasi. Silakan menunggu beberapa saat hingga ada konfirmasi dari bank. Terima kasih.");
        parentActivity().startActivity(intent);
        parentActivity().finish();


    }
}
