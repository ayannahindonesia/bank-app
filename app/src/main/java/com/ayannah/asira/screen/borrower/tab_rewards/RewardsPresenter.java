package com.ayannah.asira.screen.borrower.tab_rewards;

import androidx.annotation.Nullable;

import javax.inject.Inject;

public class RewardsPresenter implements RewardsContract.Presenter {

    @Nullable
    private RewardsContract.View mView;

    @Inject
    RewardsPresenter(){


    }

    @Override
    public void takeView(RewardsContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
