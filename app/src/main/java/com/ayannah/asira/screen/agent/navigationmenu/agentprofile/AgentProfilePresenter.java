package com.ayannah.asira.screen.agent.navigationmenu.agentprofile;

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

public class AgentProfilePresenter implements AgentProfileContract.Presenter {

    private AgentProfileContract.View mView;
    private PreferenceRepository preferenceRepository;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    AgentProfilePresenter(PreferenceRepository preferenceRepository, RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(AgentProfileContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void setAgentProfile() {
        if (mView != null) {
            mView.loadAgentProfile(preferenceRepository);
        }
    }

    @Override
    public void patchDataAgent(JsonObject jsonPatchAgentProfile) {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.patchAgentProfile(jsonPatchAgentProfile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    preferenceRepository.setAgentEmail(res.getEmail());
                    preferenceRepository.setAgentPhone(res.getPhone());
                    preferenceRepository.setAgentBanks(res.getBanks().toString().replace("[", "").replace("]", ""));
                    preferenceRepository.setAgentBanksName(res.getBanksName().toString().replace("[", "").replace("]",""));
                    mView.successUpdateProfileAgent();

                }, error -> {

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Connection Error " + " on getClientToken()");
                    } else {

                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }
                }));
    }

    @Override
    public void patchAgentPhotoProfile(String pict) {
        if (mView == null) {
            return;
        }

        JsonObject jsonImagePhotoProfile = new JsonObject();
        jsonImagePhotoProfile.addProperty("image", pict);

        mComposite.add(remoteRepository.patchAgentProfile(jsonImagePhotoProfile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    mView.successUpdatePhotoAgent();

                }, error -> {

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Connection Error " + " on getClientToken()");
                    } else {

                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }
                }));
    }

    @Override
    public void getProviderName(String agentProvider) {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.getAgentProvider(agentProvider)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {
            mView.setAgentProviderName(res.getName());
        }, err -> {
            ANError anError = (ANError) err;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Connection Error " + " on getClientToken()");
            } else {

                if (anError.getErrorBody() != null) {

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"));
                }
            }
        }));
    }
}
