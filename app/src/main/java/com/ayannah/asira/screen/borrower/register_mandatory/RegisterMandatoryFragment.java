package com.ayannah.asira.screen.borrower.register_mandatory;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class RegisterMandatoryFragment extends BaseFragment implements RegisterMandatoryContract.View, Validator.ValidationListener {

    private Validator validator;
    private boolean isPwdVisible = false;
    private int coba = 1;

    @Inject
    RegisterMandatoryContract.Presenter mPresenter;

    @NotEmpty(message = "Masukan Nama Lengkap")
    @BindView(R.id.etFullName)
    EditText etFullName;

    @Length(min = 10, message = "Minimal 10 digit, Maximal 14 digit", max = 14)
    @NotEmpty(message = "Masukan Nomor Handphone Anda", trim = true)
    @BindView(R.id.etPhone)
    EditText etPhone;

    @Email(message = "Sesuaikan Format Email")
    @BindView(R.id.etEmail)
    EditText etEmail;

    @Password(min = 1, message = "Masukan Password")
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.showPass)
    TextView showPass;

    private AlertDialog dialog;

    @Inject
    public RegisterMandatoryFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        if(mPresenter.isUserLogged()){
            loginComplete();
        }

        if(mPresenter.isAgentLogged()){
            goToAgentLandingPage();
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_reg_mandatory;
    }

//    @OnTextChanged(value = R.id.etPhone, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
//    void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        if (count<3) {
//            etPhone.setText("62");
//        }
//    }

    @OnClick(R.id.showPass)
    void visiblePass(){

        if(!isPwdVisible){

//            openPass.setVisibility(View.GONE);
//            invisiblePass.setVisibility(View.VISIBLE);

            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            etPassword.setSelection(etPassword.getText().length());

            showPass.setText("Sembunyikan");
            showPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_viewdisable, 0, 0, 0);

            isPwdVisible = true;
        } else {

            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            etPassword.setSelection(etPassword.getText().length());

            showPass.setText("Perlihatkan");
            showPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_viewenable, 0, 0, 0);

            isPwdVisible = false;
        }

    }

    @OnClick(R.id.btnTxtLoginAccount)
    void clickLoginAccount() {
        Intent intent = new Intent(parentActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @OnTextChanged(value = R.id.etPhone, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void textChanged(CharSequence s) {
        if (s.toString().equals("") || s.toString().equals("6")) {
            etPhone.setText("62");
            etPhone.setSelection(etPhone.getText().length());
        }
    }

//    @OnTextChanged(value = R.id.etPhone, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    void afterChanged(EditText s) {
//        String s1 = s.getText().toString();
////        if (s1.startsWith("0")))
//    }

//    @OnClick(R.id.etPhone)
//    void etPhoneClick() {
//        if (etPhone.getText().toString().equals("")) {
//            etPhone.setText("62");
//        }
//    }

    @Override
    protected void initView(Bundle state) {

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.btnTxtRegister)
    void registNewBorrower() {
        if (etEmail.getText().toString().equals("regist")) {
            Toast.makeText(parentActivity(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {
            validator.validate();
        }
    }

    @Override
    public void onValidationSucceeded() {
        dialog.show();
        mPresenter.getToken();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);

                switch (view.getId()){
                    case R.id.etFullName:
                        etFullName.requestFocus();
                        etFullName.setSelection(0);

                        break;
                    case R.id.etPhone:
                        etPhone.requestFocus();
                        etPhone.setSelection(0);

                        break;
                    case R.id.etEmail:
                        etEmail.requestFocus();
                        etEmail.setSelection(0);

                        break;
                    case R.id.etPassword:
                        etPassword.requestFocus();
                        etPassword.setSelection(0);

                        break;

                }
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void successCheckUnique() {
        mPresenter.requestOTP(etPhone.getText().toString(), coba);
        coba++;
    }

    @Override
    public void successRequestOTP() {
        dialog.dismiss();

        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
        intent.putExtra(VerificationOTPActivity.PURPOSES, "regist");
        intent.putExtra("personal_phone", etPhone.getText().toString());
        intent.putExtra("personal_name", etFullName.getText().toString());
        intent.putExtra("personal_email", etEmail.getText().toString());
        intent.putExtra("personal_pass", etPassword.getText().toString());
        startActivity(intent);
    }

    @Override
    public void failedGetToken(String errMessage) {
        Toast.makeText(parentActivity(), errMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedCheckUnique(String errMessage) {
        Toast.makeText(parentActivity(), errMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successGetToken() {
        mPresenter.checkUnqiue(etPhone.getText().toString(), etEmail.getText().toString());
    }

    @Override
    public void loginComplete() {

        Intent login = new Intent(parentActivity(), BorrowerLandingPage.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);

        dialog.dismiss();

        parentActivity().finish();

    }

    @Override
    public void goToAgentLandingPage() {

        Intent intent = new Intent(parentActivity(), LPAgentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        dialog.dismiss();
        parentActivity().finish();
    }
}
