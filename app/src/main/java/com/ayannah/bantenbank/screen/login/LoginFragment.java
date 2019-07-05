package com.ayannah.bantenbank.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.screen.register.addaccountbank.AddAccountBankActivity;
import com.ayannah.bantenbank.screen.register.choosebank.ChooseBankActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.progressLogin)
    LinearLayout progressLogin;

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

        if(mPresenter.isUserLogged()){
            Toast.makeText(parentActivity(), "udah pernah login", Toast.LENGTH_SHORT).show();
            loginComplete();
        }
    }

    @Override
    protected void initView(Bundle state) {

    }

    @OnClick(R.id.btnLogin)
    void onClickLogin(){

//        Intent intent = new Intent(parentActivity(), MainMenuActivity.class);
//        startActivity(intent);
        String phone = etPhone.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if(phone.isEmpty() || pass.isEmpty()){

            Toast.makeText(parentActivity(), "Nomor ponsel/Password belum diisi", Toast.LENGTH_SHORT).show();

        }else {

            progressLogin.setVisibility(View.VISIBLE);
            mPresenter.getPublicToken(phone, pass);

        }
    }

    @OnClick(R.id.btnRegister)
    void onClickRegister(){

        Intent regist = new Intent(parentActivity(), ChooseBankActivity.class);
        startActivity(regist);
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), "Error: "+message, Toast.LENGTH_SHORT).show();

        progressLogin.setVisibility(View.GONE);
    }

    @Override
    public void completeCreateUserToken() {

        mPresenter.setUserIdentity();

    }

    @Override
    public void loginComplete() {
        Toast.makeText(parentActivity(), "LoginComplete", Toast.LENGTH_SHORT).show();

        Intent login = new Intent(parentActivity(), MainMenuActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);

        progressLogin.setVisibility(View.GONE);

        parentActivity().finish();

    }
}
