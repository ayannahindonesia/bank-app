package com.ayannah.asira.screen.agent.listloan.ditolak;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

public class DitolakFragment extends BaseFragment implements DitolakContract.View {

    @Inject
    DitolakContract.Presenter mPresenter;

    @Inject
    public DitolakFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_ditolak;

    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
