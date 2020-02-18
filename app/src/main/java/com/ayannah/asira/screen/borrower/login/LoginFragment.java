package com.ayannah.asira.screen.borrower.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.agent.loginagent.LoginAgentActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.screen.register.termcondition.TermConditionActivity;
import com.ayannah.asira.screen.resetpassword.ResetPasswordActivity;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

public class LoginFragment extends BaseFragment implements
        LoginContract.View, Validator.ValidationListener {

    @Inject
    LoginContract.Presenter mPresenter;

    @NotEmpty(message = "Mohon masukkan nomor telepon anda")
    @BindView(R.id.etPhone)
    EditText etPhone;

    @NotEmpty( message = "Masukkan password anda")
    @BindView(R.id.etPassword)
    EditText etPassword;

//    @BindView(R.id.lyLogin)
//    RelativeLayout lyLogin;

//    @BindView(R.id.showPass)
//    TextView showPass;

//    @BindView(R.id.invisiblePass)
//    ImageButton invisiblePass;

    private Validator validator;
    private AlertDialog dialog;
    private boolean isPwdVisible = false;

    @Inject
    public LoginFragment(){}


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_login_new;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        if(mPresenter.isUserLogged()){
            loginComplete();
        }
    }

    @Override
    protected void initView(Bundle state) {

//        Animation slideUp = AnimationUtils.loadAnimation(parentActivity(), R.anim.slide_up);
//        lyLogin.startAnimation(slideUp);

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

//    @OnClick(R.id.showPass)
//    void visiblePass(){
//
//        if(!isPwdVisible){
//
////            openPass.setVisibility(View.GONE);
////            invisiblePass.setVisibility(View.VISIBLE);
//
//            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            etPassword.setSelection(etPassword.getText().length());
//
//            showPass.setText("Sembunyikan");
//            showPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_viewdisable, 0, 0, 0);
//
//            isPwdVisible = true;
//        } else {
//
//            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            etPassword.setSelection(etPassword.getText().length());
//
//            showPass.setText("Perlihatkan");
//            showPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_viewenable, 0, 0, 0);
//
//            isPwdVisible = false;
//        }
//
//    }

//    @OnClick(R.id.invisiblePass)
//    void invisiblePass(){
//
//        if(isPwdVisible){
//
////            openPass.setVisibility(View.VISIBLE);
////            invisiblePass.setVisibility(View.GONE);
//
//            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            etPassword.setSelection(etPassword.getText().length());
//
//            isPwdVisible = false;
//        }
//
//    }

    @OnClick(R.id.btnTxtLogin)
    void onClickLogin(){

        validator.validate();

    }

    @OnClick(R.id.resetPassword)
    void onClickResetPass(){

        Intent pass = new Intent(parentActivity(), ResetPasswordActivity.class);
        pass.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pass);
    }

    @OnClick(R.id.txtDaftar)
    void onClickRegister(){

        parentActivity().finish();

    }

    @OnClick(R.id.btnTxtLoginAgent)
    void onClickLoginAgent(){

        Intent intent = new Intent(parentActivity(), LoginAgentActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

        etPassword.setText("");

        dialog.dismiss();
    }

    @Override
    public void completeCreateUserToken() {

        mPresenter.setUserIdentity();

    }



    @Override
    public void loginComplete() {
        Toast.makeText(parentActivity(), "Login berhasil", Toast.LENGTH_SHORT).show();

        Intent login = new Intent(parentActivity(), BorrowerLandingPage.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);

        dialog.dismiss();

        parentActivity().finish();

    }

    @Override
    public void accountNotOTP() {
        dialog.dismiss();

        Toast.makeText(parentActivity(), "Akun belum terverifikasi", Toast.LENGTH_LONG).show();

        String phone = etPhone.getText().toString().trim();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("secret", "KMndM2vURIGoe1jgzYOA6RTa8qzB5k");
        jsonObject.addProperty("try", 1);

        mPresenter.postRequestOTP(jsonObject);
    }

    @Override
    public void errorFCM(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

        Intent login = new Intent(parentActivity(), BorrowerLandingPage.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);

        dialog.dismiss();

        parentActivity().finish();
    }

    @Override
    public void successGetOTP() {
        dialog.dismiss();

        Intent submit = new Intent(parentActivity(), VerificationOTPActivity.class);
        submit.putExtra(VerificationOTPActivity.PURPOSES, "resubmit_regist");
        submit.putExtra(FormOtherFragment.PHONE, etPhone.getText().toString().trim());
        submit.putExtra(FormOtherFragment.PASS, etPassword.getText().toString().trim());
        startActivity(submit);
    }

    @Override
    public void onValidationSucceeded() {

        String phone = etPhone.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if(phone.isEmpty() || pass.isEmpty()){

            Toast.makeText(parentActivity(), "Nomor ponsel/Password belum diisi", Toast.LENGTH_SHORT).show();

        }else {

            dialog.show();
            if (phone.substring(0,1).equals("0")) {
                mPresenter.getPublicToken(phone.substring(1), pass);
            } else {
                mPresenter.getPublicToken(phone, pass);
            }

        }

    }

    @Override
    public void isAccountWrong() {

        dialog.dismiss();
        Toast.makeText(parentActivity(), "No Telp/Password salah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }

    }

    @OnTextChanged(value =R.id.etPhone, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void et_onchanged_phone(Editable editable) {
        if (editable.toString().equals("") || editable.toString().equals("6")) {
            etPhone.setText("62");
            etPhone.setSelection(etPhone.getText().length());
        }
    }
}
