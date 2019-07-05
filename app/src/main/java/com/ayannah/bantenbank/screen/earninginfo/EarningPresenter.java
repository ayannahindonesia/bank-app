package com.ayannah.bantenbank.screen.earninginfo;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class EarningPresenter implements EarningContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;
    private RemoteRepository remoteRepository;

    @Nullable
    private EarningContract.View mView;

    @Inject
    EarningPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remoteRepository){

        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void takeView(EarningContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getPenghasilan() {

        if(mView == null){
            return;
        }

        mView.loadPenghasilan(preferenceRepository.getUserPrimaryIncome(),
                preferenceRepository.getUserOtherIncome(),
                preferenceRepository.getUserOtherSourceIncome());
    }
}
