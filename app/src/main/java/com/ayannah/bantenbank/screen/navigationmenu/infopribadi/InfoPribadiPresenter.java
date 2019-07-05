package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

public class InfoPribadiPresenter implements InfoPribadiContract.Presenter {

    @Nullable
    InfoPribadiContract.View mView;

    private Application application;
    private RemoteRepository remoteRepo;
    private PreferenceRepository preferenceRepository;

    @Inject
    InfoPribadiPresenter(Application application, RemoteRepository remoteRepo, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepo = remoteRepo;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void getInfoPribadiUser() {

        if(mView != null) {
            mView.loadInfoPribadi(preferenceRepository);
        }

    }

    @Override
    public void takeView(InfoPribadiContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
