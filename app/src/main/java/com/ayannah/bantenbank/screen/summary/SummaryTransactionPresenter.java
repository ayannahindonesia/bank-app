package com.ayannah.bantenbank.screen.summary;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SummaryTransactionPresenter implements SummaryTransactionContract.Presenter {

    @Nullable
    private SummaryTransactionContract.View mView;

    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    SummaryTransactionPresenter(RemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void loanApplication(JsonObject json) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.postLoan(json)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response ->{

            mView.successLoanApplication(String.valueOf(response.getId()));

        }, error ->{

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessages("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessages(jsonObject.optString("message"));
                }
            }

        }));

    }

    @Override
    public void takeView(SummaryTransactionContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void requestOTPForLoan(String idLoan) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getOTPForLoan(idLoan)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {



        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessages("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessages(jsonObject.optString("message"));
                }
            }
        }));

    }
}
