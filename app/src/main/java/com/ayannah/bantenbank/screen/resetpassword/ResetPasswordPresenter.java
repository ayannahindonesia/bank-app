package com.ayannah.bantenbank.screen.resetpassword;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {

    @Nullable
    private ResetPasswordContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    ResetPasswordPresenter(RemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void sendEmail(String email) {

    }

    @Override
    public void takeView(ResetPasswordContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;

    }
}
