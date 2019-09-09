package com.ayannah.asira.screen.register.formemailphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FormEmailPhoneFragment extends BaseFragment implements Validator.ValidationListener {

    @NotEmpty(message = "Masukan Alamat Email Anda")
    @Email(message = "Format Email Salah")
    @BindView(R.id.regist_email)
    EditText email;

    @NotEmpty(message = "Masukan Nomor Handphone Anda")
    @BindView(R.id.regist_phone)
    EditText phone;

    @Password(min = 1, message = "Masukan Password")
    @BindView(R.id.regist_pass)
    EditText pass;

    @NotEmpty(message = "Masukan Konfirmasi Password")
    @ConfirmPassword(message = "Password Tidak Cocok")
    @BindView(R.id.regist_pass_retype)
    EditText passRetype;

    private Validator validator;

    @Inject
    public FormEmailPhoneFragment(){}


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_email_phone;
    }

    @Override
    protected void initView(Bundle state) {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        bundle.putString(FormOtherFragment.EMAIL, email.getText().toString());
        bundle.putString(FormOtherFragment.PHONE, phone.getText().toString());
        bundle.putString(FormOtherFragment.PASS, pass.getText().toString());
        bundle.putString(FormOtherFragment.CONF_PASS, passRetype.getText().toString());

        Intent form = new Intent(parentActivity(), FormBorrowerActivity.class);
        form.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        form.putExtras(bundle);
        startActivity(form);
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
}
