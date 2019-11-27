package com.ayannah.asira.screen.summary;

import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
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

        AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            //check account number borrower is ada apa engga

                            checkBorrowerAccountNumber(String.valueOf(response.getInt("id")));

//                            mView.successLoanApplication(String.valueOf(response.getInt("id")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        if(anError.getErrorBody() != null){

                            Log.d("verify Loan: ", "gagal");
                            Log.d("verify Loan: ", "errpr:"+anError.getErrorCode());
                            Log.d("verify Loan: ", "body: "+anError.getErrorBody());


                        }

                    }
                });


    }

    private void checkBorrowerAccountNumber(String id) {

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

                mView.successLoanApplication(id);
            }

        }, error -> mView.showErrorMessages(CommonUtils.commonErrorFormat(error))));
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
