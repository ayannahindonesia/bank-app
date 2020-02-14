package com.ayannah.asira.screen.borrower.register_mandatory;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterMandatoryPresenter implements RegisterMandatoryContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private RegisterMandatoryContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    RegisterMandatoryPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(RegisterMandatoryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView=null;
    }

    @Override
    public void checkUnqiue(String phone, String email) {
        if (mView == null) {
            return;
        }

        mComposite.add(remotRepo.checkAccount(email, phone, "", "")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            mView.successCheckUnique();

        }, error -> {

            ANError anError = (ANError) error;

            if(anError.getErrorBody() != null) {
                JSONObject json = new JSONObject(anError.getErrorBody());
                String message = String.format("%s (kode: %s)", json.optString("message"), anError.getErrorCode());
                mView.failedCheckUnique(message);

            }else {

                mView.failedCheckUnique("Terjadi kesalahan koneksi");

            }

        }));
    }

    @Override
    public void requestOTP(String phone, int coba) {
        if (mView == null) {
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("secret", "KMndM2vURIGoe1jgzYOA6RTa8qzB5k");
        jsonObject.addProperty("try", coba);

        mComposite.add(Completable.fromAction(() -> {
            remotRepo.postOTPRequestBorrower(jsonObject);
            mView.successRequestOTP();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

    }

    @Override
    public void getToken() {
        if (mView == null) {
            return;
        }

        mComposite.add(remotRepo.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {
            preferenceRepository.setPublicToken("Bearer " + res.getToken());
            mView.successGetToken();
        }, err -> {
            mView.failedGetToken(err.getMessage());
        }));
    }

    @Override
    public boolean isUserLogged() {
        return preferenceRepository.isUserLogged();
    }
}
