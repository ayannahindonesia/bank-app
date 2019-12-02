package com.ayannah.asira.screen.agent.listloan.pengajuan;

import javax.inject.Inject;

public class PengajuanPresenter implements PengajuanContract.Presenter {

    private PengajuanContract.View mView;

    @Inject
    PengajuanPresenter(){

    }

    @Override
    public void takeView(PengajuanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
