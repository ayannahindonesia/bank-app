package com.ayannah.asira.screen.agent.loginagent;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginAgentPresenter implements LoginAgentContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private LoginAgentContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    LoginAgentPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remoteRepository) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remoteRepository = remoteRepository;
        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(LoginAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
