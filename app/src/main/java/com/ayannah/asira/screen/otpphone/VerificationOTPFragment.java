package com.ayannah.asira.screen.otpphone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.PinEntryEditText;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.screen.success.SuccessActivity;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class VerificationOTPFragment extends BaseFragment implements VerificationOTPContract.View {

    private static final String TAG = VerificationOTPActivity.class.getSimpleName();

    @BindView(R.id.etPin)
    PinEntryEditText etPin;

    @BindView(R.id.loadingProgress)
    LinearLayout pgLoading;

    @BindView(R.id.errorIndicator)
    LinearLayout lyErrorIndicator;

    @BindView(R.id.errorCode)
    TextView errorCode;

    @BindView(R.id.errorMessage)
    TextView errorMessage;

    @Inject
    @Named("purpose")
    String purpose;

    @Inject
    @Named("idloan")
    int idLoan;

    private Vibrator vibrator;
    private long[] patternVibrate ={500, 0, 500};

    @Inject
    VerificationOTPContract.Presenter mPresenter;

    private String REGISTER = "regist";
    private String PINJAMAN = "pinjaman";
    private String RESUBMIT_LOAN = "resubmit_loan";
    private String RESUBMIT_REGIST = "resubmit_regist";
    private String POST_PINJAMAN = "post_pinjaman";
    private String POST_PINJAMAN_AGENT = "post_pinjaman_agent";
    private String REGISTER_BORROWER = "REGISTER_BORROWER";

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

        vibrator = (Vibrator)parentActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //this condition for register purposes
        if(purpose.equals(REGISTER)){
            Bundle bundle = parentActivity().getIntent().getExtras();
            assert  bundle!=null;
            mPresenter.getPublicToken(bundle.getString(FormOtherFragment.PHONE), bundle.getString(FormOtherFragment.PASS), "init");
        }


    }

    @OnTextChanged(value = R.id.etPin, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(CharSequence charSequence) {
        if (charSequence.length() == 6) {

            dialog.show();

            if(purpose.equals(REGISTER)){

                registerNewAccount(charSequence);

            }else if(purpose.equals(PINJAMAN) || purpose.equals(POST_PINJAMAN)){

                loanRequest(charSequence);

            }else if(purpose.equals(RESUBMIT_LOAN)){

                resubmitLoanRequest(idLoan);
            } else if (purpose.equals(RESUBMIT_REGIST)) {

                resubmitRegister(charSequence);
            } else if (purpose.equals(POST_PINJAMAN_AGENT)) {

                loanRequestAgent(charSequence);
            } else if(purpose.equals(REGISTER_BORROWER)){

                registerBorrowerFromAgentSide(charSequence);

            }else {

                dialog.dismiss();
            }

        }
    }

    private void registerBorrowerFromAgentSide(CharSequence otpcode) {

        String id_borrower = parentActivity().getIntent().getStringExtra("id_borrower");

        Log.e(TAG, id_borrower);

        mPresenter.postOTPforRegisterBorrower(id_borrower, otpcode.toString());

    }

    private void loanRequestAgent(CharSequence charSequence) {
        String id_loan = parentActivity().getIntent().getStringExtra("id_loan");
        String otp_loan = charSequence.toString();

        JsonObject json = new JsonObject();
        json.addProperty("otp_code", otp_loan);

        mPresenter.postVerifyLoanByOTPAgent(id_loan, json);
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

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        String phone = bundle.getString(FormOtherFragment.PHONE);
        String pass = bundle.getString(FormOtherFragment.PASS);

        mPresenter.getPublicToken(phone, pass, "otp");

    }

    @Override
    public void successVerifyLoan() {

        dialog.dismiss();

        Intent intent = new Intent(parentActivity(), SuccessActivity.class);
        if (purpose.equals(POST_PINJAMAN_AGENT)) {
            intent.putExtra(SuccessActivity.SUCCESS_COND, 2);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(SuccessActivity.SUCCESS_TITLE, "Verifikasi Berhasil!");
        intent.putExtra(SuccessActivity.SUCCESS_DESC, "Pengajuan pinjaman berhasil diverifikasi. Silahkan menunggu beberapa saat hingga ada konfirmasi dari bank. Terima kasih.");
        parentActivity().startActivity(intent);
        parentActivity().finish();


    }

    @Override
    public void completeCreateUserToken() {
        mPresenter.setUserIdentity();
    }

    @Override
    public void showErrorMessage(String message, int codeError) {
        dialog.dismiss();

        lyErrorIndicator.setVisibility(View.VISIBLE);

        vibrator.vibrate(patternVibrate, -1);

        errorCode.setText(String.format("Kode: %s", codeError));
        errorMessage.setText(message);


    }

    @Override
    public void loginComplete() {
        dialog.dismiss();

        Intent login = new Intent(parentActivity(), BorrowerLandingPage.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(login);

        parentActivity().finish();
    }

    @Override
    public void successCreateBorrower() {

        dialog.dismiss();

        Intent intent = new Intent(parentActivity(), LPAgentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        parentActivity().finish();

    }
}
