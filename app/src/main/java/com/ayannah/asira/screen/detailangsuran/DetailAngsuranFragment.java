package com.ayannah.asira.screen.detailangsuran;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailAngsuranFragment extends BaseFragment {

    @BindView(R.id.paging) RecyclerView rvPaging;
    @BindView(R.id.installment) RecyclerView rvInstallment;

    @Inject
    public DetailAngsuranFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_detail_angsuran;
    }

    @Override
    protected void initView(Bundle state) {

        rvPaging.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        rvPaging.setHasFixedSize(true);

        rvInstallment.setLayoutManager(new LinearLayoutManager(parentActivity()));
        rvInstallment.setHasFixedSize(true);

    }
}
