package com.ayannah.asira.screen.borrower.profile_menu.akunsaya;

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

public class AkunSayaPresenter implements AkunSayaContract.Presenter {

//    private Application application;
    private AkunSayaContract.View mView;
    private PreferenceRepository preferenceRepository;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    AkunSayaPresenter(PreferenceRepository preferenceRepository, RemoteRepository remoteRepository){
//        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(AkunSayaContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getDataUser() {
        if (mView!=null) {
            mView.showDataUser(preferenceRepository);
        }
    }

    @Override
    public void updateDataUser(String email, String nickname, boolean isEmailChange) {

        if(mView == null){
            return;
        }

        if(isEmailChange){

            //check email already exist or nah
            mComposite.add(remoteRepository.checkEmailUser(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {

                        if(res.isStatus()){

                            updatePersonalData(email, nickname);

                        }

                    }, error -> {

                        ANError anError = (ANError) error;

                        if(anError.getErrorBody() != null) {
                            JSONObject json = new JSONObject(anError.getErrorBody());
                            String message = String.format("%s (kode: %s)", json.optString("message"), anError.getErrorCode());
                            mView.showErrorMessage(message);

                        }else {

                            mView.showErrorMessage("Terjadi kesalahan koneksi");

                        }
                    }));

        }else {

            updatePersonalData(email, nickname);

        }
    }

    private void updatePersonalData(String email, String nickname) {

        if(mView == null){
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("nickname", nickname);
        json.addProperty("email", email);

        mComposite.add(remoteRepository.updateProfile(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    preferenceRepository.setUserNickname(res.getNickname());
                    preferenceRepository.setUserEmail(res.getEmail());
                    mView.berhasil();

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error "  + " on getClientToken()");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }
                }));
    }
}
