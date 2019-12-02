package com.ayannah.asira.screen.agent.listloan.ditolak;

import javax.inject.Inject;

public class DitolakPresenter implements DitolakContract.Presenter {

    private DitolakContract.View mView;

    @Inject
    DitolakPresenter(){

    }

    @Override
    public void takeView(DitolakContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
