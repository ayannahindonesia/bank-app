package com.ayannah.asira.screen.agent.tab_pesan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class PesanFragment extends BaseFragment implements PesanContract.View {

    @Inject
    PesanContract.Presenter mPresenter;

    @Inject @Named("notif")
    CommonListAdapter adapter;

    @BindView(R.id.recyclerPesan) RecyclerView recyclerView;
    @BindView(R.id.errorMsg) TextView tvError;

    @Inject
    public PesanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_pesan;
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message, int code) {

    }

    @Override
    public void showAllNotification() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getNotification();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
