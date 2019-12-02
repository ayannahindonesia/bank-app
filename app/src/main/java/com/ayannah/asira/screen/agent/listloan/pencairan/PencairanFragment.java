package com.ayannah.asira.screen.agent.listloan.pencairan;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

public class PencairanFragment extends BaseFragment implements PencairanContract.View {

    @Inject
    public PencairanFragment(){

    }


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_pencairan;
    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
