package com.ayannah.asira.screen.otpphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.PinEntryEditText;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.screen.success.SuccessActivity;
import com.google.gson.JsonObject;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class VerificationOTPFragment extends BaseFragment implements VerificationOTPContract.View {

    private static final String TAG = VerificationOTPActivity.class.getSimpleName();

    public static CountDownTimer countDownTimer;

    @BindView(R.id.otpView)
    OtpView otpView;

//    @BindView(R.id.etPin)
//    PinEntryEditText etPin;

//    @BindView(R.id.loadingProgress)
//    LinearLayout pgLoading;
//
//    @BindView(R.id.errorIndicator)
//    LinearLayout lyErrorIndicator;
//
//    @BindView(R.id.errorCode)
//    TextView errorCode;
//
//    @BindView(R.id.errorMessage)
//    TextView errorMessage;

    @Inject
    @Named("purpose")
    String purpose;

    @Inject
    @Named("idloan")
    int idLoan;

    @BindView(R.id.txtResend)
    TextView txtResend;

    @BindView(R.id.txtWrongOTP)
    TextView txtWrongOTP;

    private String personalPhone = "";
    private String personalName = "";
    private String personalEmail = "";
    private String personalPass = "";

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
        return R.layout.content_otp_verify;
    }

    @OnTextChanged(value = R.id.otpView, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void textChanged(CharSequence s, int start, int before, int count) {
        if (count<6) {
            txtWrongOTP.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(Bundle state) {

        if (purpose.equals(REGISTER)) {
            personalPhone = parentActivity().getIntent().getStringExtra("personal_phone");
            personalName = parentActivity().getIntent().getStringExtra("personal_name");
            personalEmail = parentActivity().getIntent().getStringExtra("personal_email");
            personalPass = parentActivity().getIntent().getStringExtra("personal_pass");
        }

        countDownTimer = null;

        startCountDown();

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                countDownTimer.cancel();
                hideKeyboard(parentActivity());

                dialog.show();

                if (purpose.equals(REGISTER)) {

                    registerNewAccount(otp);

                } else if (purpose.equals(PINJAMAN) || purpose.equals(POST_PINJAMAN)) {

                    loanRequest(otp);

                } else if (purpose.equals(RESUBMIT_LOAN)) {

                    resubmitLoanRequest(otp, idLoan);
                } else if (purpose.equals(RESUBMIT_REGIST)) {

                    resubmitRegister(otp);
                } else if (purpose.equals(POST_PINJAMAN_AGENT)) {

                    loanRequestAgent(otp);
                } else if (purpose.equals(REGISTER_BORROWER)) {

                    registerBorrowerFromAgentSide(otp);

                } else {

                    dialog.dismiss();
                }
            }
        });

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

    @OnClick(R.id.txtResend)
    void resendOTP() {
        mPresenter.resendOTP(personalPhone);
    }

    private void startCountDown() {
        txtResend.setClickable(false);

        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 10000) {
                        txtResend.setText("00:0" + millisUntilFinished / 1000);
                    } else {
                        txtResend.setText("00:" + millisUntilFinished / 1000);
                    }
                }

                @Override
                public void onFinish() {
                    txtResend.setClickable(true);
                    txtResend.setText("kirim ulang OTP");
                }
            };
        }

        countDownTimer.start();

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

    private void resubmitLoanRequest(String otp, int idLoan) {
        JsonObject json = new JsonObject();
        json.addProperty("otp_code", otp);

        mPresenter.verifyLoanByOTP(String.valueOf(idLoan), json);

    }

    private void registerNewAccount(CharSequence otp) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nickname", personalName);
        jsonObject.addProperty("email", personalEmail);
        jsonObject.addProperty("phone", personalPhone);
        jsonObject.addProperty("password", personalPass);
        jsonObject.addProperty("otp_code", otp.toString());

        mPresenter.postBorrowerRegister(jsonObject);

//        String phoneNo = parentActivity().getIntent().getExtras().getString(FormOtherFragment.PHONE);
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("phone", phoneNo);
//        jsonObject.addProperty("otp_code", charSequence.toString());
//
//        mPresenter.postOTPVerify(jsonObject);

    }


    private void loanRequest(CharSequence charSequence) {
        String id_loan = parentActivity().getIntent().getStringExtra("id_loan");
        String otp_loan = charSequence.toString();

        JsonObject json = new JsonObject();
        json.addProperty("otp_code", otp_loan);

//        mPresenter.postVerifyLoanByOTP(id_loan, json);
        mPresenter.verifyLoanByOTP(id_loan, json);

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

//        lyErrorIndicator.setVisibility(View.VISIBLE);

        vibrator.vibrate(patternVibrate, -1);

//        errorCode.setText(String.format("Kode: %s", codeError));
//        errorMessage.setText(message);


    }

    @Override
    public void loginComplete() {
        countDownTimer.cancel();
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

    @Override
    public void errorFCM(String errorMessage) {
        Toast.makeText(parentActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        loginComplete();
    }

    @Override
    public void successRequestOTP() {
        countDownTimer.cancel();
        startCountDown();
    }

    @Override
    public void callAPIGetClientToken() {
        mPresenter.getClientToken(personalPhone, personalPass, "otp");
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showErrorMessageOTP(String message, int codeError) {
        dialog.dismiss();
        txtWrongOTP.setVisibility(View.VISIBLE);
        vibrator.vibrate(patternVibrate, -1);
    }

    @Override
    public void onDestroyView() {
        countDownTimer.cancel();
        super.onDestroyView();
    }
}
