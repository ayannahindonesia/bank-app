package com.ayannah.asira.screen.agent.tab_data_pinjaman;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DataPinjamanPresenter implements DataPinjamanContract.Presenter {

    @Nullable
    private DataPinjamanContract.View mView;

    private RemoteRepository remoteRepository;
    private CompositeDisposable mDisposable;


    @Inject
    DataPinjamanPresenter(RemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;

        mDisposable = new CompositeDisposable();

    }

    @Override
    public void retrieveLoans() {

        if(mView == null){
            return;
        }

        mDisposable.add(remoteRepository.getAgentLoan("0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    mView.showAllLoans(response.getData());

                }, error -> {
                    ANError anError = (ANError) error;

                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak Ada Koneksi", 0);
                    }else {
                        JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                        mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                    }
                }));
    }

    @Override
    public void takeView(DataPinjamanContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;

    }
}
