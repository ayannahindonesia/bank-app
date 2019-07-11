package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

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

public class InfoPribadiPresenter implements InfoPribadiContract.Presenter {

    @Nullable
    InfoPribadiContract.View mView;

    private Application application;
    private RemoteRepository remoteRepo;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;

    @Inject
    InfoPribadiPresenter(Application application, RemoteRepository remoteRepo, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepo = remoteRepo;
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
    public void updateInfoPribadi(JsonObject json) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepo.updateProfile(json)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            mView.successUpdateInfoPribadi();

        }, error ->{

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error "  + " on getClientToken()");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message") + " on updateInfoPribadi()");
                }
            }

        }));

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
