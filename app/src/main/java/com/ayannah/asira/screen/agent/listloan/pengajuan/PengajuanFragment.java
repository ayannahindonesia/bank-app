package com.ayannah.asira.screen.agent.listloan.pengajuan;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

public class PengajuanFragment extends BaseFragment implements PengajuanContract.View {

    @Inject
    public PengajuanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_pengajuan;
    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
