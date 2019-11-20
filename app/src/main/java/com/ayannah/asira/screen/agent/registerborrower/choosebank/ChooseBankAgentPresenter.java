package com.ayannah.asira.screen.agent.registerborrower.choosebank;

import android.app.Application;
import android.widget.Toast;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChooseBankAgentPresenter implements ChooseBankAgentContract.Presenter {

    private ChooseBankAgentContract.View mView;
    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable composite;
    private PreferenceRepository preferenceRepository;

    @Inject
    ChooseBankAgentPresenter(Application application, RemoteRepository remotRepo, PreferenceRepository preferenceRepository) {
        this.application = application;
        this.remoteRepository = remotRepo;
        this.preferenceRepository = preferenceRepository;
        composite = new CompositeDisposable();
    }

    @Override
    public void getAllBanks() {
        if(mView == null){
            Toast.makeText(application, "something wrong in setUserIdentity()", Toast.LENGTH_SHORT).show();
            return;
        }

        composite.add(remoteRepository.getAllBanksAgent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.successGetAllBanks(response);
                }, error -> {
                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak Ada Koneksi");
                    }else {
                        JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                        mView.showErrorMessage(jsonObject.optString("message"));
                    }
                }));
    }

    @Override
    public void getPublicToken() {

        composite.add(remoteRepository.getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    preferenceRepository.setPublicToken("Bearer "+response.getToken());
                    getAllBanks();

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

    @Override
    public void takeView(ChooseBankAgentContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
