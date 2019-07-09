package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import android.app.Application;

import com.ayannah.bantenbank.data.local.PreferenceRepository;

import javax.inject.Inject;

public class DataPendukungPresenter implements DataPendukungContract.Presenter {

    private Application application;
    private DataPendukungContract.View mView;
    private PreferenceRepository preferenceRepository;


    @Inject
    DataPendukungPresenter(Application application, PreferenceRepository preferenceRepository){

        this.application = application;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void takeView(DataPendukungContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getDataPendukung() {

        if (mView != null) {
            mView.showDataPendukung(preferenceRepository);
        }

    }
}
