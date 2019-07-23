package com.ayannah.bantenbank.screen.register.adddoc;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddDocumentPresenter implements AddDocumentContract.Presenter {

    @Nullable
    private AddDocumentContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    AddDocumentPresenter(Application application, RemoteRepository remoteRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void checkMandatoryItem(String ktp, String phoneNumber, String email, String npwp) {

        if(mView == null){
            Toast.makeText(application, "something wrong in chckmandotarotyItem()", Toast.LENGTH_SHORT).show();
            return;
        }

        mComposite.add(remoteRepository.checkAccount(email, phoneNumber, ktp, npwp)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            if(response.isStatus()){

                mView.successCheckMandotryEntity(response.getMessage());

            }

        }, error -> {
            ANError anError = (ANError)error;
            if(anError.getErrorBody() != null){

                try {
                    JSONObject json = new JSONObject(anError.getErrorBody());
                    Toast.makeText(application, json.optString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }));
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
