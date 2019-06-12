package com.ayannah.bantenbank.screen.historyloan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.model.Loans;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HistoryLoanPresenter implements HistoryLoanContract.Presenter {

    @Nullable
    private HistoryLoanContract.View mView;

    private Application application;

    @Inject
    HistoryLoanPresenter(Application application){

        this.application = application;
    }

    @Override
    public void loadHistoryTransaction() {

        if(mView == null){
            return;
        }

        List<Loans> results = new ArrayList<>();

        Loans loans1 = new Loans();
        loans1.setAmount(5000000);
        loans1.setLoanType("Multiguna");
        loans1.setNoLoan(1888228827);
        loans1.setStatus("Tertunda");

        Loans loans2 = new Loans();
        loans2.setAmount(45000000);
        loans2.setLoanType("Mikro");
        loans2.setNoLoan(1888228828);
        loans2.setStatus("Tidak lengkap");

        Loans loans3 = new Loans();
        loans3.setAmount(5000000);
        loans3.setLoanType("Multiguna");
        loans3.setNoLoan(1888228830);
        loans3.setStatus("Diterima");

        Loans loans4 = new Loans();
        loans4.setAmount(25000000);
        loans4.setLoanType("Mikro");
        loans4.setNoLoan(1900000907);
        loans4.setStatus("Diterima");

        Loans loans5 = new Loans();
        loans5.setAmount(10000000);
        loans5.setLoanType("Multiguna");
        loans5.setNoLoan(1900000909);
        loans5.setStatus("Ditolak");

        results.add(loans1);
        results.add(loans2);
        results.add(loans3);
        results.add(loans4);
        results.add(loans5);

        mView.showAllTransaction(results);

    }

    @Override
    public void takeView(HistoryLoanContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
