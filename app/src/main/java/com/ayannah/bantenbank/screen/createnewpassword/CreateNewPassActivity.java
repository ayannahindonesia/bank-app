package com.ayannah.bantenbank.screen.createnewpassword;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.login.LoginActivity;
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

    @Password(min = 1, message = "Masukan Password")
    @BindView(R.id.etPass)
    EditText passord;

    @NotEmpty(message = "Masukan Konfirmasi Password", trim = true)
    @ConfirmPassword(message = "Password Tidak Cocok")
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
        // ATTENTION: This was auto-generated to handle app links.
        handleIntentAppLinks();
    }

    private void handleIntentAppLinks() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            String recipeId = appLinkData.getLastPathSegment();
            Uri appData = Uri.parse("content://com.ayannah.bantenbank/recipe/").buildUpon()
                    .appendPath(recipeId).build();
            showIntent(appData);
        }
    }

    private void showIntent(Uri appData) {
        Toast.makeText(this, appData.getLastPathSegment(), Toast.LENGTH_LONG).show();
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

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Password berhasil dirubah, silahkan masuk menggunakan password baru Anda", Toast.LENGTH_SHORT).show();

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
