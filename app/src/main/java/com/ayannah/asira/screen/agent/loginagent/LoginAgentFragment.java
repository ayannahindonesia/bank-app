package com.ayannah.asira.screen.agent.loginagent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Binds;

public class LoginAgentFragment extends BaseFragment implements LoginAgentContract.View, Validator.ValidationListener {

    @Inject
    LoginAgentContract.Presenter mPresenter;

    @NotEmpty(message = "Masukan ID Pengguna Anda", trim = true)
    @BindView(R.id.etUserName)
    EditText etUserName;

    @NotEmpty(message ="Masukan Password Anda", trim = true)
    @BindView(R.id.etPassword)
    EditText etPassword;

    private Validator validator;
    private AlertDialog dialog;

    @Inject
    public LoginAgentFragment() {
    }

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

    }

    @Override
    protected void initView(Bundle state) {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.btnLogin)
    void loginAgent(){

        validator.validate();

    }

    @Override
    public void onValidationSucceeded() {
        dialog.show();
        mPresenter.getPublicToken(etUserName.getText().toString().trim(), etPassword.getText().toString().trim());
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

    @Override
    public void showErrorMessage(String errorMessage) {
        dialog.dismiss();
        Toast.makeText(parentActivity(), errorMessage, Toast.LENGTH_SHORT).show();

        etUserName.setText("");
        etPassword.setText("");
    }

    @Override
    public void loginComplete() {
        Toast.makeText(parentActivity(), "Login berhasil", Toast.LENGTH_SHORT).show();

        Intent login = new Intent(parentActivity(), LPAgentActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);

        dialog.dismiss();

        parentActivity().finish();
    }
}
