package com.ayannah.bantenbank.screen.register.choosebank;

import android.app.Application;

import com.ayannah.bantenbank.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ChooseBankPresenter implements ChooseBankContract.Presenter {

    private ChooseBankContract.View mView;
    private Application application;
    private RemoteRepository remotRepo;
    private CompositeDisposable composite;

    @Inject
    ChooseBankPresenter(Application application, RemoteRepository remotRepo){
        this.application = application;
        this.remotRepo = remotRepo;

        composite = new CompositeDisposable();
    }

    @Override
    public void loadBank() {

    }

    @Override
    public void takeView(ChooseBankContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
