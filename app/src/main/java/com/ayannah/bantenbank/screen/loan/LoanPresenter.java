package com.ayannah.bantenbank.screen.loan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

public class LoanPresenter implements LoanContract.Presenter {

    private Application application;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private LoanContract.View mView;

    @Inject
    LoanPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

    }

    @Override
    public void takeView(LoanContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
