package com.ayannah.bantenbank.screen.homemenu;

import android.app.Application;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.BeritaPromo;
import com.ayannah.bantenbank.data.model.Loans;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainMenuPresenter implements MainMenuContract.Presenter {

    private Application application;
    private MainMenuContract.View mView;


    @Inject
    MainMenuPresenter(Application application){
        this.application = application;


    }

    @Override
    public void takeView(MainMenuContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getMainMenu() {

    }

    @Override
    public void checkLoginStatus() {

    }

    @Override
    public void loadPromoAndNews() {

        List<BeritaPromo> listBeritaPromo = new ArrayList<>();

        BeritaPromo data = new BeritaPromo();
        data.setImg(R.drawable.breaking_news);
        data.setTitle("Berita 1");
        data.setDescription(application.getResources().getString(R.string.desc_example));
        listBeritaPromo.add(data);

        BeritaPromo data2 = new BeritaPromo();
        data2.setImg(R.drawable.promo_banner);
        data2.setDescription(application.getResources().getString(R.string.desc_example));
        data2.setTitle("Promo 2");
        listBeritaPromo.add(data2);

        mView.showPromoAndNews(listBeritaPromo);
    }

    @Override
    public void loadLoanhistory() {
        List<Loans> results = new ArrayList<>();

        Loans loans1 = new Loans();
        loans1.setAmount(5000000);
        loans1.setLoanType("Multiguna");
        loans1.setNoLoan(1888228827);
        loans1.setStatus("TERTUNDA");

        Loans loans2 = new Loans();
        loans2.setAmount(45000000);
        loans2.setLoanType("Mikro");
        loans2.setNoLoan(1888228828);
        loans2.setStatus("TIDAK LENGKAP");

        Loans loans3 = new Loans();
        loans3.setAmount(5000000);
        loans3.setLoanType("Multiguna");
        loans3.setNoLoan(1888228830);
        loans3.setStatus("DITERIMA");

        Loans loans4 = new Loans();
        loans4.setAmount(25000000);
        loans4.setLoanType("Mikro");
        loans4.setNoLoan(1900000907);
        loans4.setStatus("DITERIMA");

        Loans loans5 = new Loans();
        loans5.setAmount(10000000);
        loans5.setLoanType("Multiguna");
        loans5.setNoLoan(1900000909);
        loans5.setStatus("DITERIMA");

        results.add(loans1);
        results.add(loans2);
        results.add(loans3);
        results.add(loans4);
        results.add(loans5);

        mView.showLoandHistory(results);
    }
}
