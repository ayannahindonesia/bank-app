package com.ayannah.bantenbank.screen.login;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

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
    public void doLogin(String phone, String password) {

    }

    @Override
    public void testCredential() {

        mComposite.add(remotRepo.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            Log.d("token", response.getToken());

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection error");
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
