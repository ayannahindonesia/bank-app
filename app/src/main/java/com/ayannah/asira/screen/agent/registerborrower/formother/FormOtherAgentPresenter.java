package com.ayannah.asira.screen.agent.registerborrower.formother;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

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

        mComposite.add(remotRepo.postBorrowerRegisterAgent(jsonObjectRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //doing otp request using id borrower
                    otpRequest(response.getId());

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error");
                    }else {

                        mView.showErrorMessage(anError.getErrorBody());

//                        if(anError.getErrorBody() != null){
//
//                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
//
//                            String message = String.format("%s \n\n %s", jsonObject.optString("message"), jsonObject.optString("details"));
//                            mView.showErrorMessage(message);
//
//                        }
                    }

                }));
    }

    private void otpRequest(int id_borrower) {

//        Log.e(TAG, "id: "+id_borrower);
//        Log.e(TAG, "phone agent: "+preferenceRepository.getAgentPhone());

        JsonObject json = new JsonObject();
        json.addProperty("phone", preferenceRepository.getAgentPhone());
        Log.e(TAG, "agent num: "+ preferenceRepository.getAgentPhone());
        Log.e(TAG, "id_new_borrower: "+ id_borrower);

        Rx2AndroidNetworking.post(BuildConfig.API_URL + "agent/otp_request/{id}")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("id", String.valueOf(id_borrower))
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //success
                        mView.registerComplete(String.valueOf(id_borrower), preferenceRepository.getAgentPhone());

                    }

                    @Override
                    public void onError(ANError anError) {

                        if(anError.getErrorBody() != null){

                            /*
                             * 24 Maret 2020
                             * Untuk kondisi jika ada issue dari third party OTP (error code 422),
                             * salah satunya saldo habis/expired balance/dll
                             */
                            if(anError.getErrorCode() == 422){

                                mView.otpIssuedREgisterComplete(
                                        "OTP butuh perhatian",
                                        String.valueOf(id_borrower)
                                );

                            }else {

                                mView.showErrorMessage(anError.getErrorBody());

                            }
                        }else {

                            mView.showErrorMessage("Error: "+anError.getErrorCode());

                        }

                    }
                });

    }

    @Override
    public void takeView(FormOtherAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
