package com.ayannah.bantenbank.screen.historyloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.adapter.LoanAdapter;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.screen.detailloan.DetailTransaksiActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryLoanFragment extends BaseFragment implements HistoryLoanContract.View {

    @Inject
    HistoryLoanContract.Presenter mPresenter;

    @BindView(R.id.recyclerViewPinjaman)
    RecyclerView recyclerView;

    @BindView(R.id.progressLoading)
    LinearLayout progressLoading;

    @BindView(R.id.tryagain)
    LinearLayout tryagain;

    @BindView(R.id.no_data)
    TextView nodata;

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
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

        progressLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        nodata.setVisibility(View.GONE);

        tryagain.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnTryAgain)
    void onClickRefresh(){

        tryagain.setVisibility(View.GONE);

        mPresenter.loadHistoryTransaction();
    }

    @Override
    public void showAllTransaction(List<DataItem> results) {

        progressLoading.setVisibility(View.GONE);

        if(results.size() > 0){

            recyclerView.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);

            mAdapter.setLoanData(results);

        }else {

            recyclerView.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);

        }

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
