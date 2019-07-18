package com.ayannah.bantenbank.screen.createnewpassword;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CreateNewPassPresenter implements CreateNewPassContract.Presenter {

    @Nullable
    CreateNewPassContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    CreateNewPassPresenter(RemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void confirmNewPassword(String password) {

    }

    @Override
    public void takeView(CreateNewPassContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView  =null;
    }
}
