package com.ayannah.asira.screen.agent.listloan.pencairan;

import javax.inject.Inject;

public class PencairanPresenter implements PencairanContract.Presenter {

    private PencairanContract.View mView;

    @Inject
    PencairanPresenter(){

    }

    @Override
    public void takeView(PencairanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
