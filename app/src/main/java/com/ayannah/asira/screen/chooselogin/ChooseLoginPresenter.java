package com.ayannah.asira.screen.chooselogin;

import android.app.Application;

import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ChooseLoginPresenter implements ChooseLoginContract.Presenter {

    private ChooseLoginContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;
    private Application application;

    @Inject
    ChooseLoginPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;
        this.application = application;

        mComposite = new CompositeDisposable();

    }

    @Override
    public boolean isUserLogged() {
        return preferenceRepository.isUserLogged();
    }

    @Override
    public boolean isAgentLogged() { return preferenceRepository.isAgentLogged(); }

    @Override
    public void takeView(ChooseLoginContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView  = null;
    }
}
