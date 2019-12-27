package com.ayannah.asira.screen.borrower.profile_menu.editinfopribadi;

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

public class EditInfoPribadiPresenter implements EditInfoPribadiContract.Presenter {

    @Nullable
    private EditInfoPribadiContract.View mView;

    private Application application;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;
    private PreferenceRepository preferenceRepository;

    @Inject
    EditInfoPribadiPresenter(Application application, RemoteRepository remotRepo, PreferenceRepository preferenceRepository){

        this.application = application;
        this.remotRepo = remotRepo;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(EditInfoPribadiContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getInfoPribadiUser() {

        if(mView == null){
            return;
        }

        mView.loadInfoPribadi(preferenceRepository);

    }

    @Override
    public void getProvince() {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getProvinsi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){

                        mView.showProvices(response.getSemuaprovinsi());
                    }
                }, error ->{

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
    public void getDistrict(String idProvince) {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getKabupaten(idProvince)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){
                        mView.showDistrict(response.getDaftarKabupaten());
                    }
                }, error ->{

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
    public void getSubDistrict(String idDistrict) {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getKecamatan(idDistrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){
                        mView.showSubDistrict(response.getDaftarKecamatan());
                    }
                }, error ->{

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
    public void getKelurahan(String idSubDistrict) {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getKelurahan(idSubDistrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){
                        mView.showKelurahan(response.getDaftarDesa());
                    }
                }, error ->{

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
    public void updateInfoPribadi(JsonObject json) {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.updateProfile(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    preferenceRepository.setUserLastEducation(res.getLastEducation());

                    preferenceRepository.setUserMarriageStatus(res.getMarriageStatus());

                    preferenceRepository.setUserSpouseName(res.getSpouseName());

                    preferenceRepository.setSpouseBirthDate(res.getSpouseBirthday());

                    preferenceRepository.setSpouseEducation(res.getSpouseLasteducation());

                    preferenceRepository.setUserAddress(res.getAddress());

                    preferenceRepository.setUserProvince(res.getProvince());

                    preferenceRepository.setUserCity(res.getCity());

                    preferenceRepository.setSubDistrict(res.getSubdistrict());

                    preferenceRepository.setUrbanVillage(res.getUrbanVillage());

                    preferenceRepository.setUserNeighbourAssociation(res.getNeighbourAssociation());

                    preferenceRepository.setUserHamlets(res.getHamlets());

                    preferenceRepository.setUserHomePhoneNumber(res.getHomePhonenumber());

                    preferenceRepository.setLivedFor(String.valueOf(res.getLivedFor()));

                    preferenceRepository.setHomeOwnerShip(res.getHomeOwnership());

                    preferenceRepository.setDependants(res.getDependants());

                    mView.successUpfateInfoPribadi();

                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error ");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }

                }));

    }
}
