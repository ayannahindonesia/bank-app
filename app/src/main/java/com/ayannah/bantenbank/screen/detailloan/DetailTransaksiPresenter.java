package com.ayannah.bantenbank.screen.detailloan;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailTransaksiPresenter implements DetailTransaksiContract.Presenter {

    @Nullable
    private DetailTransaksiContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    DetailTransaksiPresenter(RemoteRepository remoteRepository){

        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void getInformationLoan(String idLoan) {

        if (mView == null){
            return;
        }

        mComposite.add(remoteRepository.getLoanDetails(idLoan)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            mView.loadAllInformation(res);

        }, error -> {

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
    public void takeView(DetailTransaksiContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
