package com.ayannah.bantenbank.screen.register.choosebank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.adapter.ChooseBankAdapter;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.Bank;
import com.ayannah.bantenbank.screen.register.addaccountbank.AddAccountBankActivity;
import com.ayannah.bantenbank.screen.register.addaccountbank.AddAccountBankFragment;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ChooseBankFragment extends BaseFragment implements ChooseBankContract.View {

    ChooseBankAdapter mAdapter;
    List<Bank> listBanks;

    @Inject
    ChooseBankContract.Presenter mPresenter;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rvBank)
    RecyclerView recyclerView;

    private AddAccountBankFragment fragmentadd = new AddAccountBankFragment();

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

    }

    @Override
    protected void initView(Bundle state) {

        listBanks = new ArrayList<>();
        Bank bank = new Bank();
        bank.setName("Bank Jakarta");
        bank.setLogo(0);
        listBanks.add(bank);

        Bank bank2 = new Bank();
        bank2.setName("Bank Bogor");
        bank2.setLogo(0);
        listBanks.add(bank2);

        Bank bank3 = new Bank();
        bank3.setName("Bank Palu");
        bank3.setLogo(0);
        listBanks.add(bank3);

        Bank bank4 = new Bank();
        bank4.setName("Bank Ngawi");
        bank4.setLogo(0);
        listBanks.add(bank4);

        Bank bank5 = new Bank();
        bank5.setName("Bank Yogya");
        bank5.setLogo(0);
        listBanks.add(bank5);

        Bank bank6 = new Bank();
        bank6.setName("Bank Buton");
        bank6.setLogo(0);
        listBanks.add(bank6);

        mAdapter = new ChooseBankAdapter(getActivity().getApplication());
        mAdapter.setItemBank(listBanks);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickBankListener(new ChooseBankAdapter.ChooseBankListener() {
            @Override
            public void onClickItemBank(Bank bank) {

                Bundle bundle = new Bundle();
                bundle.putString(FormOtherFragment.BANK_NAME, bank.getName());
//
//                fragmentadd.setArguments(bundle);
//
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, fragmentadd);
//                ft.addToBackStack(null);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();

                Intent adddbank = new Intent(parentActivity(), AddAccountBankActivity.class);
                adddbank.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                adddbank.putExtras(bundle);
                startActivity(adddbank);

            }
        });

    }


    @Override
    public void showErrorMessage(String message) {

    }
}
