package com.ayannah.asira.screen.agent.tab_data_pinjaman.filter;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FilterPinjamanPresenter implements  FilterPinjamanContract.Presenter {

    @Nullable
    private FilterPinjamanContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mDisposable;

    @Inject
    FilterPinjamanPresenter(Application application, RemoteRepository remoteRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;

        mDisposable = new CompositeDisposable();

    }

    @Override
    public void takeView(FilterPinjamanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        mDisposable.clear();
    }

    @Override
    public void retrieveBanks() {

        if(mView == null){
            return;
        }

        mDisposable.add(remoteRepository.getAllBanksAgent()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            mView.getAllBanks(response.getData());

        }, error ->{

            ANError anError = (ANError)error;
            mView.showErrorMessage(CommonUtils.errorResponseMessage(error), anError.getErrorCode());

        }));
    }
}
