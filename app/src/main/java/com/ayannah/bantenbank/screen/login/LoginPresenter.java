package com.ayannah.bantenbank.screen.login;

import android.app.Application;

import com.ayannah.bantenbank.data.local.PreferenceRepository;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;
    private LoginContract.View mView;

    @Inject
    LoginPresenter(Application application, PreferenceRepository preferenceRepository){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
    }



    @Override
    public void doLogin(String phone, String password) {

    }

    @Override
    public void takeView(LoginContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }
}
