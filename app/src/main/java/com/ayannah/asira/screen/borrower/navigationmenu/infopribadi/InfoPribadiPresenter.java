package com.ayannah.asira.screen.borrower.navigationmenu.infopribadi;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class InfoPribadiPresenter implements InfoPribadiContract.Presenter {

    @Nullable
    InfoPribadiContract.View mView;

    private Application application;
    private RemoteRepository remotRepo;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;

    @Inject
    InfoPribadiPresenter(Application application, RemoteRepository remoteRepo, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remotRepo = remoteRepo;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void getInfoPribadiUser() {

        if(mView != null) {
            mView.loadInfoPribadi(preferenceRepository);
        }

    }

    @Override
    public void takeView(InfoPribadiContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
