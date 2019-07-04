package com.ayannah.bantenbank.screen.otpphone;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VerificationOTPPresenter implements VerificationOTPContract.Presenter {
    private static final String TAG = VerificationOTPPresenter.class.getSimpleName();

    private Application application;
    private PreferenceRepository preferenceRepository;
    private VerificationOTPContract.View mView;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    public VerificationOTPPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.mComposite = new CompositeDisposable();
        this.remotRepo = remotRepo;
    }

    @Override
    public void takeView(VerificationOTPContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void postOTPVerify(JsonObject jsonObject) {
        mComposite.add(remotRepo.postVerifyOTP(jsonObject)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            Log.d("Succeess: ", "OTP Verified");
            Toast.makeText(application, "Success Registere!", Toast.LENGTH_LONG).show();
            mView.OTPVerified();

        }, error -> {

            if (((ANError) error).getErrorCode() == 400) {
                Toast.makeText(application, "Wrong OTP", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(application, "Connection Error", Toast.LENGTH_LONG).show();
            }

//            ANError anError = (ANError) error;
//            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
////                            mView.showErrorMessage("Connection Error");
//                Toast.makeText(application, "Connection Error", Toast.LENGTH_LONG).show();
//            } else {
//
//                if (anError.getErrorBody() != null) {
//
//                    JSONObject jsonObject2 = new JSONObject(anError.getErrorBody());
//                    Toast.makeText(application, jsonObject2.optString("message"), Toast.LENGTH_LONG).show();
//                }
//            }

        }));
    }
}
