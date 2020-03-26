package com.ayannah.asira.screen.loan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoanPresenter implements LoanContract.Presenter {

    private Application application;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;

    @Nullable
    private LoanContract.View mView;

    @Inject
    LoanPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(LoanContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getProducts(String idService) {
        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAllProducts(idService)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {
            mView.successGetProducts(response);
            mView.setBankAccountNumber(preferenceRepository.getBankAccountBorrower());
        }, error -> mView.showErrorMessage(CommonUtils.commonErrorFormat(error))));

    }

    @Override
    public void getReasonLoan() {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getReasons()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            mView.showReason(response.getData());

        }, error -> mView.showErrorMessage(CommonUtils.commonErrorFormat(error))));

    }
}
