package com.ayannah.asira.screen.agent.tab_data_pinjaman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.ChooseBankAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.screen.agent.listloan.ListLoanActivtiy;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindView;

public class DataPinjamanFragment extends BaseFragment implements DataPinjamanContract.View, ChooseBankAdapter.ChooseBankListener {

    @Inject
    DataPinjamanContract.Presenter mPresenter;

    @BindView(R.id.ly_datapinjaman)
    LinearLayout ly_datapinjaman;

    @BindView(R.id.rvBanks)
    RecyclerView recyclerView;

    @Inject
    ChooseBankAdapter adapter;

    private Snackbar snackbar;

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_data_pinjaman;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.fetchBanks();
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new GridLayoutManager(parentActivity(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message, int errorCode) {

        snackbar = Snackbar
                .make(ly_datapinjaman, String.format("%s - Kode error %s", message, errorCode), Snackbar.LENGTH_SHORT)
                .setAction("Tutup", v -> snackbar.dismiss());

        snackbar.show();

    }

    @Override
    public void showBanks(BankList bankList) {

        adapter.setItemBank(bankList.getData());

        adapter.setOnClickBankListener(this);

    }

    @Override
    public void onClickItemBank(BankDetail bank) {

        Intent intent = new Intent(parentActivity(), ListLoanActivtiy.class);
        intent.putExtra(ListLoanActivtiy.BANKID, bank.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
