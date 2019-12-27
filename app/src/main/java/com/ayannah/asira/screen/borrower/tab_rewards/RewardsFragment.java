package com.ayannah.asira.screen.borrower.tab_rewards;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

public class RewardsFragment extends BaseFragment implements RewardsContract.View {

    @Inject
    RewardsContract.Presenter mPresenter;

    @Inject
    public RewardsFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_rewards;
    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message, int errorCode) {

    }
}
