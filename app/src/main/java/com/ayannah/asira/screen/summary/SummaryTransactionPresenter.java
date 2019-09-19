package com.ayannah.asira.screen.summary;

import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.data.local.PreferenceDataSource;
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

        AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            mView.successLoanApplication(String.valueOf(response.getInt("id")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("verify Loan: ", "gagal");
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
}
