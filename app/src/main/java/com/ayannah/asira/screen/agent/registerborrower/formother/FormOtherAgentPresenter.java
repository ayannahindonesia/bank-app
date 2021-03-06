package com.ayannah.asira.screen.agent.registerborrower.formother;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.common.ANConstants;
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

public class FormOtherAgentPresenter implements FormOtherAgentContract.Presenter {

    private static final String TAG = FormOtherAgentPresenter.class.getSimpleName();

    private Application application;
    private PreferenceRepository preferenceRepository;
    private FormOtherAgentContract.View mView;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    FormOtherAgentPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.mComposite = new CompositeDisposable();
        this.remotRepo = remotRepo;
    }

    @Override
    public void postRegisterBorrower(JsonObject jsonObjectRequest) {

        Log.d(TAG, preferenceRepository.getPublicToken());

        mComposite.add(remotRepo.postBorrowerRegisterAgent(jsonObjectRequest)
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

                            String message = String.format("%s \n\n %s", jsonObject.optString("message"), jsonObject.optString("details"));
//                    mView.showErrorMessage(jsonObject.optString("message"));
                            mView.showErrorMessage(message);

                        }
                    }

                }));
    }

//    @Override
//    public void postBorrowerOTPRequest(String phone) {
////        if (mView==null) {
////            Toast.makeText(application, "test", Toast.LENGTH_LONG).show();
////            return;
////        }
//        JsonObject json = new JsonObject();
//        json.addProperty("phone", phone);
//
////        mComposite.add(remotRepo.postOTPRequestBorrower(json)
////        .subscribeOn(Schedulers.io())
////        .observeOn(AndroidSchedulers.mainThread())
////        .subscribe(response -> {
////
////            Log.d(TAG, "Success get OTP");
////            Log.d(TAG, response.message());
////            mView.successGetOTP();
////
////        }, error -> {
////
////            ANError anError = (ANError) error;
////            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
////                mView.showErrorMessage("Connection Error");
////            }else {
////
////                if(anError.getErrorBody() != null){
////
////                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
////                    mView.showErrorMessage(jsonObject.optString("message"));
////                }
////            }
////
////        }));
//
//        mComposite.add(Completable.fromAction(() -> {
//            remotRepo.postOTPRequestBorrower(json);
//            mView.successGetOTP();
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe());
//    }

//    @Override
//    public void getUserToken(String phone, String pass, String isFrom) {
//        JsonObject json = new JsonObject();
//        json.addProperty("key", phone);
//        json.addProperty("password", pass);
//
//        mComposite.add(remotRepo.getTokenClient(json)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//
//                    preferenceRepository.setUserToken("Bearer "+response.getToken());
//                    mView.successGetUserToken();
//
//                }, error -> {
//
//                    ANError anError = (ANError) error;
//                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
//                        mView.showErrorMessage("Connection Error");
//                    }else {
//                        if(anError.getErrorBody() != null){
//
//                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
//                            mView.showErrorMessage(jsonObject.optString("message")  + " getClientToken()");
//                        }
//                    }
//                }));
//    }

    @Override
    public void takeView(FormOtherAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
