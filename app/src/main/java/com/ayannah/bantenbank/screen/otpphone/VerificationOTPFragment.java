package com.ayannah.bantenbank.screen.otpphone;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

    final static String REGIST = "regist";
    final static String PINJAMAN = "pinjaman";

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

    String purpose;

    @Inject
    VerificationOTPContract.Presenter mPresenter;

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
        purpose = parentActivity().getIntent().getStringExtra("purpose");

    }

    @OnTextChanged(value = R.id.etPin, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(CharSequence charSequence) {
        if (charSequence.length() == 6) {
//            Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
//            assert bundle != null;

            String phoneNo = parentActivity().getIntent().getExtras().getString("phone");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("phone", phoneNo);
            jsonObject.addProperty("otp_code", charSequence.toString());

            mPresenter.postOTPVerify(jsonObject);
        }
    }

    @Override
    public void OTPVerified() {
        Intent intent = new Intent(parentActivity(), MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        parentActivity().startActivity(intent);
        parentActivity().finish();
    }
}
