package com.ayannah.asira.screen.agent.viewBorrower;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.remote.RemoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewBorrowerPresenter implements ViewBorrowerContract.Presenter {

    @Nullable
    private ViewBorrowerContract.View mView;

    private RemoteRepository remoteRepository;

    @Inject
    ViewBorrowerPresenter(){

    }

    @Override
    public void takeView(ViewBorrowerContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getDataBorrower() {
        if(mView == null){
            return;
        }

        List<NasabahAgent> results = new ArrayList<>();

        NasabahAgent data_1 = new NasabahAgent();
        data_1.setId(1);
        data_1.setName("John Doe");
        data_1.setStatus("aktif");
        data_1.setNomorRekening("-");
        data_1.setPinjamanKe("-");
        data_1.setAlamat("jalan menuju kemenangan");
        data_1.setNomorHp("081212760018");
        data_1.setNamaPasangan("Brigita");
        data_1.setPekerjaan("Karyawan swasta");
        results.add(data_1);

        NasabahAgent data_2 = new NasabahAgent();
        data_2.setId(2);
        data_2.setName("John Roe");
        data_2.setStatus("aktif");
        data_2.setNomorRekening("-");
        data_2.setPinjamanKe("-");
        data_2.setAlamat("jalan menuju kemenangan");
        data_2.setNomorHp("081212760018");
        data_2.setNamaPasangan("Brigita");
        data_2.setPekerjaan("Karyawan swasta");
        results.add(data_2);

        NasabahAgent data_3 = new NasabahAgent();
        data_3.setId(3);
        data_3.setName("John Sins");
        data_3.setStatus("aktif");
        data_3.setNomorRekening("-");
        data_3.setPinjamanKe("-");
        data_3.setAlamat("jalan menuju kemenangan");
        data_3.setNomorHp("081212760018");
        data_3.setNamaPasangan("Brigita");
        data_3.setPekerjaan("Karyawan swasta");
        results.add(data_3);

        mView.getAllData(results);

    }
}
