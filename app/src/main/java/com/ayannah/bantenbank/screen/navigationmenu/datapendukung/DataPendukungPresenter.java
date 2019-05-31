package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import android.app.Application;

import javax.inject.Inject;

public class DataPendukungPresenter implements DataPendukungContract.Presenter {

    private Application application;
    private DataPendukungContract.View mView;


    @Inject
    DataPendukungPresenter(Application application){
        this.application = application;
    }

    @Override
    public void takeView(DataPendukungContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
