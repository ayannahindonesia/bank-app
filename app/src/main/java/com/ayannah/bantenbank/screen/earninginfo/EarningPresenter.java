package com.ayannah.bantenbank.screen.earninginfo;

import android.app.Application;

import javax.inject.Inject;

public class EarningPresenter implements EarningContract.Presenter {

    private Application application;
    private EarningContract.View mView;

    @Inject
    EarningPresenter(Application application){

        this.application = application;
    }

    @Override
    public void takeView(EarningContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
