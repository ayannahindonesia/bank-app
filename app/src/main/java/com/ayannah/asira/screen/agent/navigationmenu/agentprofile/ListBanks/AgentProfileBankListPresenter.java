package com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AgentProfileBankListPresenter implements AgentProfileBankListContract.Presenter {

    private AgentProfileBankListContract.View mView;
    private PreferenceRepository preferenceRepository;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    AgentProfileBankListPresenter(PreferenceRepository preferenceRepository, RemoteRepository remoteRepository){
        this.preferenceRepository = preferenceRepository;
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(AgentProfileBankListContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getAllBanks() {
        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAllBanks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> mView.successGetAllBanks(response), error -> {
                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak Ada Koneksi");
                    }else {
                        JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                        mView.showErrorMessage(jsonObject.optString("message"));
                    }
                }));
    }
}
