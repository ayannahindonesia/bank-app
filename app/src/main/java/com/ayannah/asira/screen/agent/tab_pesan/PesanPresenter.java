package com.ayannah.asira.screen.agent.tab_pesan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PesanPresenter implements PesanContract.Presenter {

    @Nullable
    private PesanContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mDisposable;

    @Inject
    PesanPresenter(Application application, RemoteRepository remoteRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;

        mDisposable = new CompositeDisposable();

    }

    @Override
    public void takeView(PesanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getNotification() {

    }
}
