package com.ayannah.asira.screen.historyloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.LoanAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.dialog.BottomSortHistoryLoan;
import com.ayannah.asira.screen.detailloan.DetailTransaksiActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryLoanFragment extends BaseFragment implements
        HistoryLoanContract.View,
        BottomSortHistoryLoan.SortHistoryLoanListener {

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

    private BottomSortHistoryLoan bottomSortHistoryLoan;

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

        mPresenter.loadHistoryTransaction("");
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        bottomSortHistoryLoan = new BottomSortHistoryLoan();

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

        mPresenter.loadHistoryTransaction("");
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
            intent.putExtra(DetailTransaksiActivity.ID_LOAN, String.valueOf(loans.getId()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

    }

    @OnClick(R.id.sortBuStatus)
    void onClickSort(){

        bottomSortHistoryLoan.showNow(parentActivity().getSupportFragmentManager(), "fragment");
        bottomSortHistoryLoan.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void onClickStatus(String status) {

        mPresenter.loadHistoryTransaction(status);

        bottomSortHistoryLoan.dismiss();
    }
}
