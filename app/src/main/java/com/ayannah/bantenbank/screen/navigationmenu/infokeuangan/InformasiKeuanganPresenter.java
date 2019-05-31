package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import android.app.Application;

import javax.inject.Inject;

public class InformasiKeuanganPresenter implements InformasiKeuanganContract.Presenter {

    private Application application;
    private InformasiKeuanganContract.View mView;

    @Inject
    InformasiKeuanganPresenter(Application application){
        this.application = application;

    }

    @Override
    public void takeView(InformasiKeuanganContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
