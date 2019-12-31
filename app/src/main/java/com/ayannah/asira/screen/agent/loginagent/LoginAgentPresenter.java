package com.ayannah.asira.screen.agent.loginagent;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginAgentPresenter implements LoginAgentContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private LoginAgentContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    LoginAgentPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remoteRepository) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remoteRepository = remoteRepository;
        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(LoginAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getPublicToken(String username, String password) {
        if(mView == null){
            Toast.makeText(application, "something wrong in getToken", Toast.LENGTH_SHORT).show();
            return;
        }

        mComposite.add(remoteRepository.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {
            preferenceRepository.setPublicToken("Bearer "+response.getToken());
            getClientToken(username, password);
        }, error -> {
            ANError anError = (ANError) error;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Tidak Ada Koneksi");
            } else if (anError.getErrorBody() != null) {
                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                mView.showErrorMessage(jsonObject.optString("message ") + anError.getErrorBody());
            }
        }));
    }

    private void getClientToken(String username, String password) {
        if(mView == null){
            Toast.makeText(application, "something wrong in getToken", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("key", username);
        json.addProperty("password", password);

        mComposite.add(remoteRepository.getClientAgentToken(json)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            if (response.getMessage() != null) {
                mView.showErrorMessage(response.getMessage());
            } else {
                preferenceRepository.setUserToken("Bearer " + response.getToken());
                getAgentProfile();
            }

        }, error -> {

            ANError anError = (ANError) error;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Tidak Ada Koneksi");
            } else if (anError.getErrorBody() != null) {
                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                mView.showErrorMessage(jsonObject.optString("message") + anError.getErrorBody());
            }

        }));
    }

    private void getAgentProfile() {
        if(mView == null){
            Toast.makeText(application, "something wrong in getAgentProfile", Toast.LENGTH_SHORT).show();
            return;
        }

        mComposite.add(remoteRepository.getAgentProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    preferenceRepository.setAgentId(String.valueOf(response.getId()));
                    preferenceRepository.setAgentName(response.getName());
                    preferenceRepository.setAgentUserName(response.getUsername());
                    preferenceRepository.setAgentEmail(response.getEmail());
                    preferenceRepository.setAgentPhone(response.getPhone());
                    preferenceRepository.setAgentProvider(String.valueOf(response.getAgentProvider().getInt64()));
                    preferenceRepository.setAgentCategory(response.getCategory());
                    preferenceRepository.setAgentBanks(response.getBanks().toString().replace("[", "").replace("]", ""));
                    preferenceRepository.setAgentBanksName(response.getBanksName().toString().replace("[", "").replace("]",""));
                    preferenceRepository.setAgentStatus(response.getStatus());
                    preferenceRepository.setAgentLogged(true);

                    mView.loginComplete();

                }, error -> {
                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Tidak Ada Koneksi");
                    } else if (anError.getErrorBody() != null) {
                        JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                        mView.showErrorMessage(jsonObject.optString("message") + anError.getErrorBody());
                    }
                }));
    }
}
