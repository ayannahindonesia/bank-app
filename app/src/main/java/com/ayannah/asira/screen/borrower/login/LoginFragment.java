package com.ayannah.asira.screen.borrower.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.screen.borrower.homemenu.MainMenuActivity;
import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.borrower.otpphone.VerificationOTPActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.screen.register.termcondition.TermConditionActivity;
import com.ayannah.asira.screen.resetpassword.ResetPasswordActivity;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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

    private Validator validator;
    private AlertDialog dialog;

    @Inject
    public LoginFragment(){}


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_login;
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
//            Toast.makeText(parentActivity(), "udah pernah login", Toast.LENGTH_SHORT).show();
            loginComplete();
        }
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @OnClick(R.id.btnLogin)
    void onClickLogin(){

        validator.validate();

    }

    @OnClick(R.id.resetPassword)
    void onClickResetPass(){

        Intent pass = new Intent(parentActivity(), ResetPasswordActivity.class);
        pass.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pass);
    }

    @OnClick(R.id.btnRegister)
    void onClickRegister(){

        Intent kebijakan = new Intent(parentActivity(), TermConditionActivity.class);
        startActivity(kebijakan);

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

        Intent login = new Intent(parentActivity(), MainMenuActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);

        dialog.dismiss();

        parentActivity().finish();

    }

    @Override
    public void accountNotOTP() {
        dialog.dismiss();

        Toast.makeText(parentActivity(), "Akun belum terverifikasi", Toast.LENGTH_LONG).show();

        String phone = String.format("62%s", etPhone.getText().toString().trim());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phone);

        mPresenter.postRequestOTP(jsonObject);
    }

    @Override
    public void successGetOTP() {
        dialog.dismiss();

        Intent submit = new Intent(parentActivity(), VerificationOTPActivity.class);
        submit.putExtra(VerificationOTPActivity.PURPOSES, "resubmit_regist");
        submit.putExtra(FormOtherFragment.PHONE, String.format("62%s", etPhone.getText().toString().trim()));
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
                mPresenter.getPublicToken("62" + phone.substring(1), pass);
            } else {
                mPresenter.getPublicToken("62" + phone, pass);
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
        if (editable.toString().equals("0")) {
            etPhone.setText("");
            Toast.makeText(getContext(), "Masukan tanpa diawali angka 0", Toast.LENGTH_LONG).show();
        }
    }
}
