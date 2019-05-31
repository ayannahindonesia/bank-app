package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import android.os.Bundle;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;

import javax.inject.Inject;

public class DataPendukungFragment extends BaseFragment implements DataPendukungContract.View {

    @Inject
    DataPendukungContract.Presenter mPresenter;

    @Inject
    public DataPendukungFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_data_pendukung;
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
