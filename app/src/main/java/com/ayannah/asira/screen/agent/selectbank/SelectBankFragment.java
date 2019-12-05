package com.ayannah.asira.screen.agent.selectbank;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.NestedSelectBankAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BankTypeDummy;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SelectBankFragment extends BaseFragment implements SelectBankContract.View {

    @Inject
    SelectBankContract.Presenter mPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    NestedSelectBankAdapter adapter;

    @Inject
    public SelectBankFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_select_bank;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getBanks();
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBanks(List<BankTypeDummy> results) {

        adapter.setDataBankType(results);

    }
}
