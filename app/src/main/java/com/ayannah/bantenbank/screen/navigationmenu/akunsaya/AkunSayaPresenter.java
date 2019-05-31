package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import android.app.Application;

import javax.inject.Inject;

public class AkunSayaPresenter implements AkunSayaContract.Presenter {

    private Application application;
    private AkunSayaContract.View mView;

    @Inject
    AkunSayaPresenter(Application application){
        this.application = application;
    }

    @Override
    public void takeView(AkunSayaContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
