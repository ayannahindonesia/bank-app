package com.ayannah.asira.screen.borrower.register_mandatory;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RegisterMandatoryPresenter implements RegisterMandatoryContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private RegisterMandatoryContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    RegisterMandatoryPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(RegisterMandatoryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView=null;
    }
}
