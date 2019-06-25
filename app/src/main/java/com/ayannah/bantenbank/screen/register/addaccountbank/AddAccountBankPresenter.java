package com.ayannah.bantenbank.screen.register.addaccountbank;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.local.PreferenceRepository;

import javax.inject.Inject;

public class AddAccountBankPresenter implements AddAccountBankContract.Presenter {

    @Nullable
    AddAccountBankContract.View mView;

    private Application application;
    private PreferenceRepository prefRepo;

    @Inject
    AddAccountBankPresenter(Application application, PreferenceRepository prefRepo){
        this.application = application;
        this.prefRepo = prefRepo;

    }


    @Override
    public void loadAllBank() {

    }

    @Override
    public void takeView(AddAccountBankContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }
}
