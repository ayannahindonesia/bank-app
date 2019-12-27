package com.ayannah.asira.screen.borrower.profile_menu.infopribadi;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class InfoPribadiPresenter implements InfoPribadiContract.Presenter {

    @Nullable
    InfoPribadiContract.View mView;

    private Application application;
    private RemoteRepository remotRepo;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;

    @Inject
    InfoPribadiPresenter(Application application, RemoteRepository remoteRepo, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remotRepo = remoteRepo;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void getInfoPribadiUser() {

        if(mView != null) {
            mView.loadInfoPribadi(preferenceRepository);
        }

    }

    @Override
    public void takeView(InfoPribadiContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
