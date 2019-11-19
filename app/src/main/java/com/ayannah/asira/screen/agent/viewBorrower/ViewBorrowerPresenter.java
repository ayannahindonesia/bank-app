package com.ayannah.asira.screen.agent.viewBorrower;

import androidx.annotation.Nullable;

import javax.inject.Inject;

public class ViewBorrowerPresenter implements ViewBorrowerContract.Presenter {

    @Nullable
    private ViewBorrowerContract.View mView;


    @Inject
    ViewBorrowerPresenter(){

    }

    @Override
    public void takeView(ViewBorrowerContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
