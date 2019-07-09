package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import android.app.Application;

import com.ayannah.bantenbank.data.local.PreferenceRepository;

import javax.inject.Inject;

public class AkunSayaPresenter implements AkunSayaContract.Presenter {

    private Application application;
    private AkunSayaContract.View mView;
    private PreferenceRepository preferenceRepository;

    @Inject
    AkunSayaPresenter(Application application, PreferenceRepository preferenceRepository){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void takeView(AkunSayaContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getDataUser() {
        if (mView!=null) {
            mView.showDataUser(preferenceRepository);
        }
    }
}
