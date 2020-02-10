package com.ayannah.asira.screen.borrower.register_mandatory;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
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

    @Inject
    public RegisterMandatoryFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_reg_mandatory;
    }


    @OnFocusChange(R.id.etPhone)
    void etPhoneFocus() {
        if (etPhone.getText().toString().equals("")) {
            etPhone.setText("62");
        }
    }

//    @OnTextChanged(value = R.id.etPhone, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
//    void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        if (count<3) {
//            etPhone.setText("62");
//        }
//    }

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

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.btnTxtRegister)
    void registNewBorrower() {
        if (etEmail.getText().toString().equals("")) {
            Toast.makeText(parentActivity(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {
            validator.validate();
        }
    }

    @Override
    public void onValidationSucceeded() {
        //hit API new register
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
}
