package com.ayannah.asira.screen.agent.viewBorrower;

import android.os.Bundle;

import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

public class ViewBorrowerFragment extends BaseFragment implements ViewBorrowerContract.View {

    @Inject
    ViewBorrowerContract.Presenter mPresenter;

    @Inject
    public ViewBorrowerFragment(){

    }

    @Override
    protected int getLayoutView() {
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
