package com.ayannah.asira.screen.agent.listloan.pengajuan;

import com.ayannah.asira.data.model.DummyLoanBorrower;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PengajuanPresenter implements PengajuanContract.Presenter {

    private PengajuanContract.View mView;

    @Inject
    PengajuanPresenter(){

    }

    @Override
    public void takeView(PengajuanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getOnProcessLoan() {

        if(mView == null){
            return;
        }

        List<DummyLoanBorrower> results = new ArrayList<>();

        DummyLoanBorrower data = new DummyLoanBorrower();
        data.setId("1");
        data.setAdminFee("15000");
        data.setAngsuran(12);
        data.setConvenieceFee("12000");
        data.setDateRequest("21 November 2019");
        data.setDetailTujuan("Untuk minjam uang, pinjam aja.");
        data.setImbal(10);
        data.setLayanan("Layanan layananku");
        data.setName("Dinand");
        data.setNamaProduk("Product orange");
        data.setPinjamanPokok("12000000");
        data.setStatus("Dalam proses");
        data.setTotal(13240000);
        data.setTenor(12);
        data.setTujuan("Pinjam");
        results.add(data);

        DummyLoanBorrower data_d = new DummyLoanBorrower();
        data_d.setId("2");
        data_d.setAdminFee("15000");
        data_d.setAngsuran(12);
        data_d.setConvenieceFee("12000");
        data_d.setDateRequest("20 November 2019");
        data_d.setDetailTujuan("Untuk minjam uang, pinjam aja.");
        data_d.setImbal(10);
        data_d.setLayanan("Layanan layananku");
        data_d.setName("Christian");
        data_d.setNamaProduk("Product apple");
        data_d.setPinjamanPokok("12000000");
        data_d.setStatus("Dalam proses");
        data_d.setTotal(13240000);
        data_d.setTenor(12);
        data_d.setTujuan("Pinjam");
        results.add(data_d);

        mView.showOnProcessLoan(results);
    }
}
