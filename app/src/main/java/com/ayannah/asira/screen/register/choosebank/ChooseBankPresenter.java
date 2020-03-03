package com.ayannah.asira.screen.register.choosebank;

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

public class ChooseBankPresenter implements ChooseBankContract.Presenter {

    private ChooseBankContract.View mView;
    private Application application;
    private RemoteRepository remotRepo;
    private CompositeDisposable composite;
    private PreferenceRepository preferenceRepository;

    @Inject
    ChooseBankPresenter(Application application, RemoteRepository remotRepo, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remotRepo = remotRepo;
        this.preferenceRepository = preferenceRepository;

        composite = new CompositeDisposable();
    }

    @Override
    public void loadBank() {

    }

    @Override
    public void getAllBanks() {
        if(mView == null){
            return;
        }

        composite.add(remotRepo.getAllBanks()
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

        composite.add(remotRepo.getToken()
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
    public void getAllServices() {
        if (mView == null) {
            return;
        }

        composite.add(remotRepo.getAllServices()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            mView.showDescription(res.getData());

        }, err -> {

            ANError anError = (ANError) err;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Tidak Ada Koneksi");
            } else if (anError.getErrorBody() != null) {
                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                mView.showErrorMessage(jsonObject.optString("message ") + anError.getErrorBody());
            }

        }));
    }

    @Override
    public void takeView(ChooseBankContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
