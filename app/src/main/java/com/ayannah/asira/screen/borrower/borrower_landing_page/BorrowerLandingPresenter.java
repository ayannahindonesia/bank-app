package com.ayannah.asira.screen.borrower.borrower_landing_page;

import androidx.annotation.Nullable;

import javax.inject.Inject;

public class BorrowerLandingPresenter implements BorrowerLandingContract.Presenter{

    @Nullable
    private BorrowerLandingContract.View mView;

    @Inject
    BorrowerLandingPresenter(){

    }

    @Override
    public void takeView(BorrowerLandingContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
