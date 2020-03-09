package com.ayannah.asira.screen.agent.tab_data_pinjaman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.ChooseBankAdapter;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.Loans.Loans;
import com.ayannah.asira.dialog.BottomErrorHandling;
import com.ayannah.asira.screen.agent.listloan.ListLoanActivtiy;
import com.ayannah.asira.screen.agent.tab_data_pinjaman.filter.FilterPinjamanActivity;
import com.ayannah.asira.screen.detailloan.DetailTransaksiActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class DataPinjamanFragment extends BaseFragment implements DataPinjamanContract.View, ChooseBankAdapter.ChooseBankListener {

    @Inject
    DataPinjamanContract.Presenter mPresenter;

    @BindView(R.id.rvPinjaman)
    RecyclerView recyclerPinjaman;

    @Inject @Named("loan")
    CommonListAdapter adapter;

    private Snackbar snackbar;
    private int FILTER = 5;

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_data_pinjaman;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.retrieveLoans("", "", "");

    }

    @Override
    protected void initView(Bundle state) {

        recyclerPinjaman.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerPinjaman.setHasFixedSize(true);
        recyclerPinjaman.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message, int errorCode) {

        BottomErrorHandling error = new BottomErrorHandling(message, errorCode);
        error.showNow(parentActivity().getSupportFragmentManager(), "error");
        error.setOnClickListener(new BottomErrorHandling.BottomSheetErrorListener() {
            @Override
            public void onClickClose(int code) {
                error.dismiss();
            }
        });

    }

    @Override
    public void showAllLoans(List<DataItem> results) {

        adapter.setListAgentLoan(results);
        adapter.setOnClickListenerLoanInAgent(new CommonListListener.LoanAdapterListener() {
            @Override
            public void onClickItem(DataItem loans) {

                Log.e("statusAgent", loans.getStatus());

                Intent intent = new Intent(parentActivity(), DetailTransaksiActivity.class);
                intent.putExtra(DetailTransaksiActivity.ID_LOAN, String.valueOf(loans.getId()));
                intent.putExtra(DetailTransaksiActivity.LOAN_DETAIL, loans);
                intent.putExtra("purpose", DetailTransaksiActivity.FROMAGENT);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClickItemBank(BankDetail bank) {

        Intent intent = new Intent(parentActivity(), ListLoanActivtiy.class);
        intent.putExtra(ListLoanActivtiy.BANKID, bank.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.filter)
    void onClickFilter(){

        Intent intent = new Intent(parentActivity(), FilterPinjamanActivity.class);
        startActivityForResult(intent, FILTER);

    }
}
