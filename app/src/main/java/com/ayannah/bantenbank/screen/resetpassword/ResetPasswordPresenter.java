package com.ayannah.bantenbank.screen.resetpassword;

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

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {

    @Nullable
    private ResetPasswordContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;

    @Inject
    ResetPasswordPresenter(RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void clientAuth(String email) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            preferenceRepository.setPublicToken("Bearer "+res.getToken());

            sendEmail(email);

        }, error ->{
            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message") + " on getClientToken()");
                }
            }

        }));
    }

    @Override
    public void sendEmail(String email) {

        if(mView == null){
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("email", email);

        mComposite.add(remoteRepository.sendEmailToResetPassword(json)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            if(response.isStatus()){

                mView.showSuccessSendEmail(response.getMessage());
            }

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"));
                }
            }

        }));

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
