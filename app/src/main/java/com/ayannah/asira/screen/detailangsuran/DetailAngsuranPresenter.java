package com.ayannah.asira.screen.detailangsuran;

import com.ayannah.asira.data.model.Angsuran;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DetailAngsuranPresenter implements DetailAngsuranContract.Presenter {

    private DetailAngsuranContract.View mView;

    @Inject
    DetailAngsuranPresenter(){

    }

    @Override
    public void dataProcessing() {

        List<Angsuran> results = new ArrayList<>();

        Angsuran one = new Angsuran();
        one.setPage("1");
        String[] dataOne = {"11","12","13","14","15","16","17","18"};
        one.setData(dataOne);

        Angsuran two = new Angsuran();
        two.setPage("2");
        String[] dataTwo = {"21","22","23","24","25","26","27","28"};
        two.setData(dataTwo);

        Angsuran tre = new Angsuran();
        tre.setPage("3");
        String[] dataTre = {"31","32","33","34","35","36","37","38"};
        tre.setData(dataTre);

        Angsuran frt = new Angsuran();
        frt.setPage("4");
        String[] dataFrt = {"41","42","43","44","45","46","47","48"};
        frt.setData(dataFrt);

        results.add(one);
        results.add(two);
        results.add(tre);
        results.add(frt);

        mView.showAllPaging(results);
    }

    @Override
    public void takeView(DetailAngsuranContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
