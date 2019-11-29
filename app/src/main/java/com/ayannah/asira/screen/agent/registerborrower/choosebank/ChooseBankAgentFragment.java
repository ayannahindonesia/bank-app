package com.ayannah.asira.screen.agent.registerborrower.choosebank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.ChooseBankAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.screen.agent.registerborrower.addaccountbank.AddAccountBankAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.formother.FormOtherAgentFragment;
import com.ayannah.asira.screen.agent.services.ListServicesAgentActivity;
import com.ayannah.asira.screen.agent.viewBorrower.ViewBorrowerActivity;
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ChooseBankAgentFragment extends BaseFragment implements ChooseBankAgentContract.View {

    ChooseBankAdapter mAdapter;
    List<BankDetail> listBanks;

    private AddAccountBankFragment fragmentadd = new AddAccountBankFragment();
    private AlertDialog dialog;

    @Inject
    ChooseBankAgentContract.Presenter mPresenter;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rvBank)
    RecyclerView recyclerView;

    @Inject
    public ChooseBankAgentFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_choose_bank;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getPublicToken();
//        mPresenter.getAllBanks();

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
//        dialog.dismiss();
        listBanks = new ArrayList<>();
        for (int i=0; i<bankList.getTotalData(); i++) {
            BankDetail bankDetail = new BankDetail();
            bankDetail.setName(bankList.getData().get(i).getName());
            bankDetail.setId(bankList.getData().get(i).getId());

            listBanks.add(bankDetail);
        }

        mAdapter = new ChooseBankAdapter(getActivity().getApplication());
        mAdapter.setItemBank(listBanks);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickBankListener(new ChooseBankAdapter.ChooseBankListener() {
            @Override
            public void onClickItemBank(BankDetail bank) {

                Intent addbank;
                if (getActivity().getIntent().getStringExtra("isFrom").equals("regBorrower")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FormOtherAgentFragment.BANK_ID, String.valueOf(bank.getId()));
                    bundle.putString(FormOtherAgentFragment.BANK_NAME, bank.getName());
                    addbank = new Intent(parentActivity(), AddAccountBankAgentActivity.class);
                    addbank.putExtras(bundle);
                } else {
//                    addbank = new Intent(parentActivity(), AddAccountBankAgentActivity.class);
                    addbank = new Intent(parentActivity(), ViewBorrowerActivity.class);
                    addbank.putExtra(ViewBorrowerActivity.BANK_ID, String.valueOf(bank.getId()));

                }
                addbank.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(addbank);

            }
        });
    }
}
