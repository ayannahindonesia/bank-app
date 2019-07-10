package com.ayannah.bantenbank.screen.loan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoanPresenter implements LoanContract.Presenter {

    private Application application;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;

    @Nullable
    private LoanContract.View mView;

    @Inject
    LoanPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(LoanContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

}
