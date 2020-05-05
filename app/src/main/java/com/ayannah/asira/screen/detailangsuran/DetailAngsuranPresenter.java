package com.ayannah.asira.screen.detailangsuran;

import android.util.Log;

import com.ayannah.asira.data.model.Angsuran;
import com.ayannah.asira.data.model.InstallmentDetails;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DetailAngsuranPresenter implements DetailAngsuranContract.Presenter {

    private final static String TAG = DetailAngsuranPresenter.class.getSimpleName();

    private DetailAngsuranContract.View mView;

    @Inject
    DetailAngsuranPresenter(){

    }

    @Override
    public void dataProcessing(ArrayList<InstallmentDetails> results) {

        ArrayList<Angsuran> angsurans = new ArrayList<>();

        double totalRows = results.size();

        double amountPage = totalRows/12; //per halaman hanya akan menampilkan 12 record

        //jika hasil dari amount page adalah desimal, maka amount page + 1
        if(totalRows % 12 != 0){
            amountPage++;
        }
        int page = (int)amountPage;

        int rows = results.size();
        int latestIndex = 0;
        for(int i=0; i < page; i++){

            Angsuran data = new Angsuran();
            data.setPage(String.valueOf(i+1));

            ArrayList<InstallmentDetails> records = new ArrayList<>();
            for(int j = latestIndex; j < rows; j++){

                //a page only assing until 12 records data
                records.add(results.get(j));
                latestIndex = j+1;
                if(records.size() == 12){
                    break;
                }
            }
            data.setData(records);

            //assign object angsuran into array list
            angsurans.add(data);
        }

        mView.showAllPaging(angsurans);

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
