package com.ayannah.bantenbank.screen.register.formothers;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.screen.login.LoginContract;
import com.ayannah.bantenbank.screen.login.LoginPresenter;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FormOtherPresenter implements FormOtherContract.Presenter{

    private static final String TAG = FormOtherPresenter.class.getSimpleName();

    private Application application;
    private PreferenceRepository preferenceRepository;
    private FormOtherContract.View mView;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    public FormOtherPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.mComposite = new CompositeDisposable();
        this.remotRepo = remotRepo;
    }

    @Override
    public void postRegisterBorrower(JsonObject jsonObjectRequest) {
        Log.d(TAG, preferenceRepository.getPublicToken());

        mComposite.add(remotRepo.postBorrowerRegister(jsonObjectRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            Log.d(TAG, "Register Borrower Complete");

            mView.registerComplete();

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
    public void postBorrowerOTPRequest(String phone) {
//        if (mView==null) {
//            Toast.makeText(application, "test", Toast.LENGTH_LONG).show();
//            return;
//        }
        JsonObject json = new JsonObject();
        json.addProperty("phone", phone);

//        mComposite.add(remotRepo.postOTPRequestBorrower(json)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(response -> {
//
//            Log.d(TAG, "Success get OTP");
//            Log.d(TAG, response.message());
//            mView.successGetOTP();
//
//        }, error -> {
//
//            ANError anError = (ANError) error;
//            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
//                mView.showErrorMessage("Connection Error");
//            }else {
//
//                if(anError.getErrorBody() != null){
//
//                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
//                    mView.showErrorMessage(jsonObject.optString("message"));
//                }
//            }
//
//        }));

        mComposite.add(Completable.fromAction(() -> {
            remotRepo.postOTPRequestBorrower(json);
            mView.successGetOTP();
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe());
    }

    @Override
    public void takeView(FormOtherContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
