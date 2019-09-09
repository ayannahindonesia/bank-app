package com.ayannah.asira.screen.earninginfo;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EarningPresenter implements EarningContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Nullable
    private EarningContract.View mView;

    @Inject
    EarningPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remoteRepository){

        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(EarningContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getPenghasilan() {

        if(mView == null){
            return;
        }

        mView.loadPenghasilan(preferenceRepository.getUserPrimaryIncome(),
                preferenceRepository.getUserOtherIncome(),
                preferenceRepository.getUserOtherSourceIncome());
    }

    @Override
    public void updateUserIncome(int primaryIncome, int secondaryIncome, String otherIncomeSource) {

        if(mView == null){
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("monthly_income", primaryIncome);
        json.addProperty("other_income", secondaryIncome);
        json.addProperty("other_incomesource", otherIncomeSource);

        mComposite.add(remoteRepository.updateProfile(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    preferenceRepository.setUserPrimaryIncome(String.valueOf(res.getMonthlyIncome()));
                    preferenceRepository.setUserOtherIncome(String.valueOf(res.getOtherIncome()));
                    preferenceRepository.setuserOtherSourceIncome(res.getOtherIncomesource());

                    mView.completeUpdateIncome();


                }, error -> {
                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message")  + " updateUserIncome()");
                        }
                    }
                }));

    }
}
