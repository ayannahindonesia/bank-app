package com.ayannah.bantenbank.screen.historyloan;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.adapter.LoanAdapter;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.Loans;
import com.ayannah.bantenbank.screen.detailloan.DetailTransaksiActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HistoryLoanFragment extends BaseFragment implements HistoryLoanContract.View {

    @Inject
    HistoryLoanContract.Presenter mPresenter;

    @BindView(R.id.recyclerViewPinjaman)
    RecyclerView recyclerView;

    @Inject
    LoanAdapter mAdapter;

    @Inject
    public HistoryLoanFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_history_loan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.loadHistoryTransaction();
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showAllTransaction(List<Loans> results) {

        mAdapter.setLoanData(results);

        mAdapter.setLoanListener(loans -> {

            Intent intent = new Intent(parentActivity(), DetailTransaksiActivity.class);
            startActivity(intent);

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
