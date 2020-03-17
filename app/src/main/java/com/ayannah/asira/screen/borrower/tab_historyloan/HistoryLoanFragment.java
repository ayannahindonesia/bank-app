package com.ayannah.asira.screen.borrower.tab_historyloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.dialog.BottomDialogHandlingError;
import com.ayannah.asira.dialog.BottomSortHistoryLoan;
import com.ayannah.asira.screen.detailloan.DetailTransaksiActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryLoanFragment extends BaseFragment implements
        HistoryLoanContract.View,
        BottomSortHistoryLoan.SortHistoryLoanListener {

    private final String TAG = HistoryLoanFragment.class.getSimpleName();

    @Inject
    HistoryLoanContract.Presenter mPresenter;

    @BindView(R.id.recyclerViewPinjaman)
    RecyclerView recyclerView;

    @BindView(R.id.tryagain)
    LinearLayout tryagain;

    @BindView(R.id.no_data)
    TextView nodata;

    @Inject @Named("pinjaman")
    CommonListAdapter mAdapterLoans;

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

        mPresenter.getProducts();
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapterLoans);

        bottomSortHistoryLoan = new BottomSortHistoryLoan();

    }

    @Override
    public void showErrorMessage(String message, int code) {

        BottomDialogHandlingError error = new BottomDialogHandlingError(message, code);
        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
        error.setOnClickLister(error::dismiss);

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

        if(results.size() > 0){

            recyclerView.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);

            mAdapterLoans.setDateLoans(results);

            mAdapterLoans.setOnClickListenerLoanInAgent(loans -> {

                Intent intent = new Intent(parentActivity(), DetailTransaksiActivity.class);
                intent.putExtra(DetailTransaksiActivity.ID_LOAN, String.valueOf(loans.getId()));
                intent.putExtra(DetailTransaksiActivity.LOAN_DETAIL, loans);
                intent.putExtra("purpose", DetailTransaksiActivity.FROMBORROWER);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            });

        }else {

            recyclerView.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);

        }

    }

//    @OnClick(R.id.sortBuStatus)
//    void onClickSort(){
//
//        if(bottomSortHistoryLoan != null && bottomSortHistoryLoan.isAdded()){
//            return;
//        }
//
//        bottomSortHistoryLoan.showNow(parentActivity().getSupportFragmentManager(), "fragment");
//        bottomSortHistoryLoan.setOnClickListener(this);
//
//    }

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
