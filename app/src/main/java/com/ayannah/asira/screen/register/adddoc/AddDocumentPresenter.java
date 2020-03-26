package com.ayannah.asira.screen.register.adddoc;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddDocumentPresenter implements AddDocumentContract.Presenter {

    @Nullable
    private AddDocumentContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;
    private PreferenceRepository preferenceRepository;

    @Inject
    AddDocumentPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository) {
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void checkMandatoryItem(String ktp, String phoneNumber, String email, String npwp) {

        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.checkAccount(email, phoneNumber, ktp, npwp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (response.isStatus()) {

                        mView.successCheckMandotryEntity(response.getMessage(), phoneNumber);

                    }

                }, error -> {
                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Tidak Ada Koneksi");
                    } else {

                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        } else {

                            mView.showErrorMessage(String.format("Bad gateway (%s)", anError.getErrorCode()));
                        }

                    }

                }));
    }

    @Override
    public void getProvince() {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.getProvinsi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!response.isSuccess()) {

                        mView.showProvices(response.getSemuaprovinsi());
                    }
                }, error -> {

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Connection error");
                    } else {
                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));
    }

    @Override
    public void getCity(String idProvince) {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.getKabupaten(idProvince)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!response.isSuccess()) {
                        mView.showCity(response.getDaftarKabupaten());
                    }
                }, error -> {

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Connection error");
                    } else {
                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));

    }

    @Override
    public void getKecamatan(String idCity) {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.getKecamatan(idCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!response.isSuccess()) {
                        mView.showKecamatan(response.getDaftarKecamatan());
                    }
                }, error -> {

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Connection error");
                    } else {
                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));
    }

    @Override
    public void getKelurahan(String idKecamatan) {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.getKelurahan(idKecamatan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!response.isSuccess()) {
                        mView.showKelurahan(response.getDaftarDesa());
                    }
                }, error -> {

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Connection error");
                    } else {
                        if (anError.getErrorBody() != null) {

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));
    }

    @Override
    public void getCurrentProfile() {
        if (mView == null) {
            return;
        }

        mView.showCurrentProfile(preferenceRepository);
    }

    @Override
    public void patchUserProfile(JsonObject jsonObject) {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.updateProfile(jsonObject)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            preferenceRepository.setUserName(res.getFullname());
            preferenceRepository.setUserAddress(res.getAddress());
            preferenceRepository.setBankID(res.getBank().getInt64());
            preferenceRepository.setBankAccountBorrower(res.getBankAccountnumber());
            preferenceRepository.setUserCity(res.getCity());
            preferenceRepository.setDepartment(res.getDepartment());
            preferenceRepository.setFieldToWork(res.getFieldOfWork());
            preferenceRepository.setUserHamlets(res.getHamlets());
            preferenceRepository.setIDCardImage(res.getIdCardImage());
            preferenceRepository.setIdCardUser(res.getIdcardNumber());
            preferenceRepository.setUserMotherName(res.getMotherName());
            preferenceRepository.setOccupation(res.getOccupation());
            preferenceRepository.setUserProvince(res.getProvince());
            preferenceRepository.setSubDistrict(res.getSubdistrict());
            preferenceRepository.setUrbanVillage(res.getUrbanVillage());
            preferenceRepository.setTaxCardImg(res.getTaxIDImage());
            preferenceRepository.setTaxCard(res.getTaxidNumber());

            mView.successUpdateProfile();

        }, err -> {

            ANError anError = (ANError) err;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Connection error");
            } else {
                if (anError.getErrorBody() != null) {

                    JSONObject jsonObject2 = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(anError.getErrorBody());
                    Log.e("patchUserProfile", anError.getErrorBody());

                }else {

                    mView.showErrorMessage(anError.getErrorBody());
                }
            }

        }));
    }

    @Override
    public void takeView(AddDocumentContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
