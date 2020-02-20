package com.ayannah.asira.screen.borrower.register_personal_info;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RegisterPersonalInfoPresenter implements RegisterPersonalInfoContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private RegisterPersonalInfoContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    RegisterPersonalInfoPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(RegisterPersonalInfoContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
