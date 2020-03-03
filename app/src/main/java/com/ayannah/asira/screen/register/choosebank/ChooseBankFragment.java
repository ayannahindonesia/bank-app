package com.ayannah.asira.screen.register.choosebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.ChooseBankAdapter;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.adapter.ServiceDescriptionAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankActivity;
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankFragment;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseBankFragment extends BaseFragment implements ChooseBankContract.View {

    ChooseBankAdapter mAdapter;
    ServiceDescriptionAdapter serviceDescriptionAdapter;

    @Inject
    ChooseBankContract.Presenter mPresenter;

    @BindView(R.id.rvBank)
    RecyclerView recyclerView;

    @BindView(R.id.bankDeskripsi)
    TextView bankDeskripsi;

    @BindView(R.id.recyclerViewBanks)
    RecyclerView recyclerViewBanks;

    private AlertDialog dialog;
    private boolean expand = false;

    @Inject
    public ChooseBankFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_choose_bank;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();
        mPresenter.getPublicToken();

        mPresenter.getAllServices();
    }

    @Override
    protected void initView(Bundle state) {

    }


    @Override
    public void showErrorMessage(String message) {
        dialog.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successGetAllBanks(BankList bankList) {
        dialog.dismiss();

        mAdapter = new ChooseBankAdapter(getActivity().getApplication());

        ArrayList<BankDetail> sortedBD = new ArrayList<>(bankList.getData());
        Collections.sort(sortedBD, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        mAdapter.setItemBank(sortedBD);

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickBankListener(bank -> {

            Bundle bundle = new Bundle();
            bundle.putString(FormOtherFragment.BANK_NAME, bank.getName());
            bundle.putInt(FormOtherFragment.BANK_ID, bank.getId());
            bundle.putString(FormOtherFragment.BANK_LOGO, bank.getImage());

            Intent adddbank = new Intent(parentActivity(), AddAccountBankActivity.class);
            adddbank.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            adddbank.putExtras(bundle);
            startActivity(adddbank);

        });
    }

    @Override
    public void showDescription(List<BankService.Data> data) {

        serviceDescriptionAdapter = new ServiceDescriptionAdapter(getActivity().getApplication());
        serviceDescriptionAdapter.setServiceDesc(data);

        recyclerViewBanks.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerViewBanks.setHasFixedSize(true);
        recyclerViewBanks.setAdapter(serviceDescriptionAdapter);

    }

    @OnClick(R.id.bankDeskripsi)
    void expandBankDeskripsi () {
        if (expand) {
            bankDeskripsi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, R.drawable.ic_down, 0);
            expand = false;
            recyclerViewBanks.setVisibility(View.GONE);
        } else {
            bankDeskripsi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, R.drawable.ic_up, 0);
            expand = true;
            recyclerViewBanks.setVisibility(View.VISIBLE);
        }
    }
}
