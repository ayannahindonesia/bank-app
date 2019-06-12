package com.ayannah.bantenbank.screen.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.adapter.ChooseBankAdapter;
import com.ayannah.bantenbank.data.model.Bank;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseBank extends Fragment {

    ChooseBankAdapter mAdapter;
    List<Bank> listBanks;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rvBank)
    RecyclerView recyclerView;

//    private FormEmailPhone fragment = new FormEmailPhone();
    private AddAccountBank fragmentadd = new AddAccountBank();

    public ChooseBank(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_bank, container, false);
        ButterKnife.bind(this, view);

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
                bundle.putString(AddAccountBank.BANK_NAME, bank.getName());

                fragmentadd.setArguments(bundle);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragmentadd);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });

        return view;
    }

}
