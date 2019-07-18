package com.ayannah.bantenbank.screen.register.adddoc;

import androidx.annotation.Nullable;

import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AddDocumentPresenter implements AddDocumentContract.Presenter {

    @Nullable
    AddDocumentContract.View mView;

    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    AddDocumentPresenter(RemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void checkMandatoryItem(String ktp, String phoneNumber, String email) {

    }

    @Override
    public void takeView(AddDocumentContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
