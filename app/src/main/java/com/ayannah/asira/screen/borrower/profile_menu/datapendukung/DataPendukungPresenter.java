package com.ayannah.asira.screen.borrower.profile_menu.datapendukung;

import android.app.Application;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.UserProfile;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DataPendukungPresenter implements DataPendukungContract.Presenter {

    private Application application;
    private DataPendukungContract.View mView;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposit;
    private RemoteRepository remotrepo;


    @Inject
    DataPendukungPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remoteRepository){

        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotrepo = remoteRepository;
        mComposit = new CompositeDisposable();

    }

    @Override
    public void takeView(DataPendukungContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getDataPendukung() {

        if (mView != null) {
            mView.showDataPendukung(preferenceRepository);
        }

    }

    @Override
    public void patchOtherData(JsonObject jsonObject) {
        mComposit.add(remotrepo.updateProfile(jsonObject)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            updateLocalData(response);

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak Ada Koneksi");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject2 = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject2.optString("message"));
                }
            }

        }));
    }

    private void updateLocalData(UserProfile response) {
        preferenceRepository.setUserRelatedPersonName(response.getRelatedPersonname());
        preferenceRepository.setUserRelatedRelation(response.getRelatedRelation());
        preferenceRepository.setUserRelatedAddress(response.getRelatedAddress());
        preferenceRepository.setUserRelatedHomeNumber(response.getRelatedHomenumber());
        preferenceRepository.setUserRelatedPhoneNumber(response.getRelatedPhonenumber());

        mView.successUpdateOtherData();
    }
}
