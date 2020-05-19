package com.ayannah.asira.screen.summary;

import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.R;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SummaryTransactionPresenter implements SummaryTransactionContract.Presenter {

    @Nullable
    private SummaryTransactionContract.View mView;

    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;
    private PreferenceRepository preferenceRepository;

    @Inject
    SummaryTransactionPresenter(RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void loanApplication(JsonObject json) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getUserLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    if(res.getBankAccountnumber().isEmpty() || res.getBankAccountnumber() == null){

                        mView.cannotMakingLoan();

                    }else {

                        preferenceRepository.setBankAccountBorrower(res.getBankAccountnumber());

                        createLoan(json);
                    }

                }, error -> {
                    mView.showErrorMessages(CommonUtils.commonErrorFormat(error));
                }));


    }

    private void createLoan(JsonObject json) {

        AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(mView != null){

                                mView.successLoanApplication(String.valueOf(response.getInt("id")));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        if(mView != null) {

                            if (anError.getErrorBody() != null) {

                                Log.d("verify Loan: ", "gagal");
                                Log.d("verify Loan: ", "errpr:" + anError.getErrorCode());
                                Log.d("verify Loan: ", "body: " + anError.getErrorBody());

                                try {
                                    JSONObject obj = new JSONObject(anError.getErrorBody());
                                    String message = obj.optString("message");

                                    mView.errorSendLoan(message, anError.getErrorCode());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else {

                                mView.errorSendLoan("Mohon coba beberapa saat lagi. Sedang dalam perbaikan", anError.getErrorCode());
                            }

                        }

                    }
                });


    }

    @Override
    public void takeView(SummaryTransactionContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void requestOTPForLoan(String idLoan) {

        if(mView == null){
            return;
        }

        mComposite.add(Completable.fromAction(() -> {

            remoteRepository.getOTPForLoan(idLoan);

            mView.successGetOtp("888999", idLoan);
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe());

    }

    @Override
    public void postLoanAgent(JsonObject json) {
        if (mView == null) {
            return;
        }

        AndroidNetworking.post(BuildConfig.API_URL + "agent/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if(mView != null){

                                mView.successLoanApplication(String.valueOf(response.getInt("id")));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    @Override
    public void requestOTPForLoanAgent(String id_loan) {
        if(mView == null){
            return;
        }

        mComposite.add(Completable.fromAction(() -> {

            remoteRepository.getOTPForLoanAgent(id_loan);

            mView.successGetOtp("888999", id_loan);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    public void requestOTPPersonal(boolean isPersonal, int percobaan) {

        JsonObject jsonObject = new JsonObject();
        if (isPersonal) {
            jsonObject.addProperty("phone", preferenceRepository.getUserPhone());
        } else {
            jsonObject.addProperty("phone", preferenceRepository.getAgentPhone());
        }
        jsonObject.addProperty("secret", "KMndM2vURIGoe1jgzYOA6RTa8qzB5k");
        jsonObject.addProperty("try", percobaan);

        mComposite.add(Completable.fromAction(() -> {
            remoteRepository.postOTPRequestBorrower(jsonObject);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

    }

    @Override
    public String getFormInfoLocal() {
        return preferenceRepository.getFormInfoLocal();
    }
}
