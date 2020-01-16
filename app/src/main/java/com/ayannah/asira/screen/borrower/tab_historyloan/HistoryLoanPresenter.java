package com.ayannah.asira.screen.borrower.tab_historyloan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.ServiceProductInterface;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryLoanPresenter implements HistoryLoanContract.Presenter {

    @Nullable
    private HistoryLoanContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;
    private ServiceProductInterface serviceProductInterface;

    @Inject
    HistoryLoanPresenter(Application application, RemoteRepository remoteRepository, ServiceProductInterface serviceProductInterface){
        this.remoteRepository = remoteRepository;
        this.application = application;
        this.serviceProductInterface = serviceProductInterface;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void loadHistoryTransaction(String status) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAllLoans(status)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response ->{

            mView.showAllTransaction(response.getData());

        }, error ->{

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }

        }));

    }

    @Override
    public void getProducts() {
        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAllProductsGlobal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    serviceProductInterface.setTotalData(response.getTotalData());
                    serviceProductInterface.setServiceProducts(response.getProducts());

                    loadHistoryTransaction("");

                }, error -> {
                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
                    } else if (anError.getErrorBody() != null) {

                        JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                        mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());

                    } else {
                        mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                    }

                }));
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
