package com.ayannah.bantenbank.screen.createnewpassword;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class CreateNewPassActivity extends DaggerAppCompatActivity implements CreateNewPassContract.View,
        Validator.ValidationListener {

    @Inject
    CreateNewPassContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Password
    @NotEmpty(trim = true, message = "Mohon masukkan password baru anda")
    @Length(max = 10, min = 6, message = "Minimum password  6 digit")
    @BindView(R.id.etPass)
    EditText passord;

    @ConfirmPassword
    @NotEmpty(trim = true, message = "Mohon ketik ulang password anda")
    @BindView(R.id.etRetype)
    EditText retypePassword;

    private Unbinder mUnbinder;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_pass);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Buat Password Baru");

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.btnSubmit)
    void onSubmitButton(){

        validator.validate();

    }

    @Override
    public void onBackPressed() {
        noticeDialog();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void noticeDialog() {

        finish();

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successCreateNewPassword() {

    }

    @Override
    public void onValidationSucceeded() {

        Toast.makeText(this, "Succeed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for(ValidationError error: errors){

            String message = error.getCollatedErrorMessage(this);
            View view = error.getView();

            if(view instanceof EditText){

                ((EditText)view).setError(message);
            }else {

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
