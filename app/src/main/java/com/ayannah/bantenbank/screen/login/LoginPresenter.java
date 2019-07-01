package com.ayannah.bantenbank.screen.login;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private Application application;
    private PreferenceRepository preferenceRepository;
    private LoginContract.View mView;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    LoginPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }


    @Override
    public void getClientToken(String phone, String pass) {

        JsonObject json = new JsonObject();
        json.addProperty("key", phone);
        json.addProperty("password", pass);

        Log.d(TAG, preferenceRepository.getPublicToken());

        mComposite.add(remotRepo.getTokenClient(json)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            preferenceRepository.setUserToken("Bearer "+response.getToken());

            Log.d(TAG, "create token client complete");

            //set User Identity
            setUserIdentity();

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
    public void setUserIdentity() {

        mComposite.add(remotRepo.getUserLogin()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            preferenceRepository.setUserEmail(response.getEmail());
            preferenceRepository.setUserName(response.getEmployerName());
            preferenceRepository.setUserPhone(response.getEmployerNumber());
            preferenceRepository.setUserNIP(String.valueOf(response.getIdcardNumber()));
            preferenceRepository.setIdUser(String.valueOf(response.getEmployeeId()));
            preferenceRepository.setUserPrimaryIncome(String.valueOf(response.getMonthlyIncome()));
            preferenceRepository.setUserOtherIncome(String.valueOf(response.getOtherIncome()));
            preferenceRepository.setuserOtherSourceIncome(response.getOtherIncomesource());
            preferenceRepository.setUserBank(String.valueOf(response.getBank()));

            preferenceRepository.setUserSetup(true);

            mView.loginComplete();

        }, error ->{

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
    public void takeView(LoginContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }
}
