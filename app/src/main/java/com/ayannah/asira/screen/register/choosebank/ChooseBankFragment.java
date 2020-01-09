package com.ayannah.asira.screen.register.choosebank;

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
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankActivity;
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankFragment;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ChooseBankFragment extends BaseFragment implements ChooseBankContract.View {

    ChooseBankAdapter mAdapter;

    @Inject
    ChooseBankContract.Presenter mPresenter;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rvBank)
    RecyclerView recyclerView;

    private AddAccountBankFragment fragmentadd = new AddAccountBankFragment();
    private AlertDialog dialog;

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
//        mAdapter.setItemBank(bankList.getData());

        ArrayList<BankDetail> sortedBD = new ArrayList<>(bankList.getData());
        Collections.sort(sortedBD, new Comparator<BankDetail>() {
            @Override
            public int compare(BankDetail o1, BankDetail o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        mAdapter.setItemBank(sortedBD);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickBankListener(new ChooseBankAdapter.ChooseBankListener() {
            @Override
            public void onClickItemBank(BankDetail bank) {

                Bundle bundle = new Bundle();
                bundle.putString(FormOtherFragment.BANK_NAME, bank.getName());
                bundle.putInt(FormOtherFragment.BANK_ID, bank.getId());

                Intent adddbank = new Intent(parentActivity(), AddAccountBankActivity.class);
                adddbank.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                adddbank.putExtras(bundle);
                startActivity(adddbank);

            }
        });
    }
}
