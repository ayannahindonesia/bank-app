package com.ayannah.asira.screen.borrower.borrower_landing_page;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;

import javax.inject.Inject;

public class BorrowerLandingPresenter implements BorrowerLandingContract.Presenter{

    @Nullable
    private BorrowerLandingContract.View mView;
    private PreferenceRepository prefRepo;

    @Inject
    BorrowerLandingPresenter(PreferenceRepository prefRepo){
        this.prefRepo = prefRepo;
    }

    @Override
    public void takeView(BorrowerLandingContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public boolean getIsLogin() {
        return prefRepo.isUserLogged();
    }
}
