package com.ayannah.bantenbank.screen.loan;

import android.app.Application;

import javax.inject.Inject;

public class LoanPresenter implements LoanContract.Presenter {

    private Application application;
    private LoanContract.View mView;

    @Inject
    LoanPresenter(Application application){
        this.application = application;

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
