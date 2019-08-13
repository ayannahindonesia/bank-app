package com.ayannah.bantenbank.screen.createnewpassword;

import android.app.Application;
import android.widget.Toast;

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

public class CreateNewPassPresenter implements CreateNewPassContract.Presenter {

    private CreateNewPassContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;
    private Application application;

    @Inject
    CreateNewPassPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;
        this.application = application;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void confirmNewPassword(String password) {

    }

    @Override
    public void setUserToken(String lastPathSegment) {
        preferenceRepository.setUserToken("Bearer " + lastPathSegment);
    }

    @Override
    public void postResetPassword(String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);

        mComposite.add(remoteRepository.postNewPassword(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    mView.successCreateNewPassword();

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject json = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(json.optString("message"));
                        }
                    }

                }));
    }

    @Override
    public void takeView(CreateNewPassContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView  = null;
    }
}
