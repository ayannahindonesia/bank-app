package com.ayannah.asira.screen.createnewpassword;

import android.app.Application;

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
    public void setUserToken(String userToken) {
        preferenceRepository.setUserToken("Bearer " + userToken);
    }

    @Override
    public void postResetPassword(String password, String uuid) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("uuid", uuid);

        mComposite.add(remoteRepository.getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    setUserToken(response.getToken());
                    callAPICreateNewPass(jsonObject);

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak Ada Koneksi");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject json = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(json.optString("message"));
                        }
                    }

                })
        );
    }

    private void callAPICreateNewPass(JsonObject jsonObject) {
        mComposite.add(remoteRepository.postNewPassword(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    mView.successCreateNewPassword();

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Tidak Ada Koneksi");
                    } else if (anError.getErrorCode() == 404) {
                        mView.showErrorMessage("ID Tidak Ditemukan");
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
