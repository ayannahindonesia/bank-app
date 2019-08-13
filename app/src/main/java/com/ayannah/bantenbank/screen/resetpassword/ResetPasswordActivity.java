package com.ayannah.bantenbank.screen.resetpassword;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.success.SuccessActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class ResetPasswordActivity extends DaggerAppCompatActivity implements ResetPasswordContract.View, Validator.ValidationListener {

    @Inject
    ResetPasswordContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NotEmpty(message = "Masukan Alamat Email Anda")
    @Email(message = "Format Email Salah")
    @BindView(R.id.etEmail)
    EditText email;

    private Unbinder mUnbinder;
    private Validator validator;
    private AlertDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Reset Password");

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @OnClick(R.id.btnSubmit)
    void onClickButton(){

        validator.validate();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void showErrorMessage(String message) {

        dialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSuccessSendEmail(String message) {
        dialog.dismiss();

        Intent success = new Intent(this, SuccessActivity.class);
        success.putExtra(SuccessActivity.SUCCESS_TITLE, "Kirim email Berhasil");
        success.putExtra(SuccessActivity.SUCCESS_DESC, message);
        success.putExtra(SuccessActivity.SUCCESS_COND, 1);
        startActivity(success);
        finish();
    }

    @Override
    public void onValidationSucceeded() {

        dialog.show();
        mPresenter.clientAuth(email.getText().toString());

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for(ValidationError error: errors){

            String msg = error.getCollatedErrorMessage(this);
            View view = error.getView();

            if(view instanceof EditText){

                ((EditText)view).setError(msg);

            }else {

                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
