package com.ayannah.asira.screen.agent.selectbank;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.model.BankDummy;
import com.ayannah.asira.data.model.BankTypeDummy;
import com.ayannah.asira.data.remote.RemoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SelectBankPresenter implements SelectBankContract.Presenter {

    @Nullable
    private SelectBankContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    SelectBankPresenter(Application application, RemoteRepository remoteRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void getBanks() {

        if(mView == null){
            return;
        }

        List<BankTypeDummy> bankType = new ArrayList<>();

        //batch 1
        List<BankDummy> bank = new ArrayList<>();
        BankDummy y = new BankDummy();
        y.setName("Jawa Barat");
        bank.add(y);

        BankDummy y1 = new BankDummy();
        y1.setName("Sumsel");
        bank.add(y1);

        BankDummy y2 = new BankDummy();
        y2.setName("Manado");
        bank.add(y2);

        BankTypeDummy x = new BankTypeDummy();
        x.setType("BPD");
        x.setBanks(bank);

        bankType.add(x);

        //batch 2
        List<BankDummy> bank2 = new ArrayList<>();

        BankDummy z = new BankDummy();
        z.setName("Bekasi");
        bank2.add(z);

        BankDummy z1 = new BankDummy();
        z1.setName("Karawang");
        bank2.add(z1);

        BankDummy z2 = new BankDummy();
        z2.setName("Kuningan");
        bank2.add(z2);

        BankTypeDummy x2 = new BankTypeDummy();
        x2.setType("BPR");
        x2.setBanks(bank);

        bankType.add(x2);

        mView.showBanks(bankType);

    }

    @Override
    public void takeView(SelectBankContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }
}
