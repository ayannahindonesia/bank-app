package com.ayannah.bantenbank.screen.historyloan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryLoanPresenter implements HistoryLoanContract.Presenter {

    @Nullable
    private HistoryLoanContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    HistoryLoanPresenter(Application application, RemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;
        this.application = application;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void loadHistoryTransaction() {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAllLoans()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response ->{

            mView.showAllTransaction(response.getData());

        }, error ->{

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"));
                }
            }

        }));

    }

    @Override
    public void sortHistoryBy(String status) {

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
