package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import android.os.Bundle;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;

import javax.inject.Inject;

public class AkunSayaFragment extends BaseFragment implements AkunSayaContract.View {

    @Inject
    AkunSayaContract.Presenter mPresenter;

    @Inject
    public AkunSayaFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_akunsaya;
    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
