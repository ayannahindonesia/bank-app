package com.ayannah.bantenbank.screen.login;

import android.content.Intent;
import android.os.Bundle;

import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.screen.register.addaccountbank.AddAccountBankActivity;

import javax.inject.Inject;

import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

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

        mPresenter.testCredential();
    }

    @Override
    protected void initView(Bundle state) {

    }

    @OnClick(R.id.btnLogin)
    void onClickLogin(){

        Intent intent = new Intent(parentActivity(), MainMenuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnRegister)
    void onClickRegister(){

        Intent regist = new Intent(parentActivity(), AddAccountBankActivity.class);
        regist.putExtra("purpose", "newRegist");
        startActivity(regist);
    }

    @Override
    public void showErrorMessage(String message) {

    }
}
