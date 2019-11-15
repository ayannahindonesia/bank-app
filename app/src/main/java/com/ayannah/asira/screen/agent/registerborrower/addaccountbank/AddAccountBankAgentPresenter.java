package com.ayannah.asira.screen.agent.registerborrower.addaccountbank;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;

import javax.inject.Inject;

public class AddAccountBankAgentPresenter implements AddAccountBankAgentContract.Presenter {

    @Nullable
    AddAccountBankAgentContract.View mView;

    private Application application;
    private PreferenceRepository prefRepo;

    @Inject
    AddAccountBankAgentPresenter(Application application, PreferenceRepository prefRepo){
        this.application = application;
        this.prefRepo = prefRepo;

    }

    @Override
    public void takeView(AddAccountBankAgentContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }
}
