package com.ayannah.bantenbank.data.remote;

import com.androidnetworking.common.Priority;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.Kabupaten;
import com.ayannah.bantenbank.data.model.Kecamatan;
import com.ayannah.bantenbank.data.model.Kelurahan;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.ayannah.bantenbank.data.model.Token;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.Credentials;

public class RemoteDataSource implements RemoteRepository {

    private PreferenceRepository preferenceRepository;

    @Inject
    RemoteDataSource(PreferenceRepository preferenceRepository){
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public Single<Provinsi> getProvinsi() {

        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Provinsi.class);

    }

    @Override
    public Single<Kabupaten> getKabupaten(String idProvinsi) {
        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi/{idprovinsi}/kabupaten")
                .addPathParameter("idprovinsi", idProvinsi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kabupaten.class);
    }

    @Override
    public Single<Kecamatan> getKecamatan(String idKabupaten) {
        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi/kabupaten/{idkabupaten}/kecamatan")
                .addPathParameter("idkabupaten", idKabupaten)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kecamatan.class);
    }

    @Override
    public Single<Kelurahan> getKelurahan(String idKecamatan) {
        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi/kabupaten/kecamatan/{idkecamatan}/desa")
                .addPathParameter("idkecamatan", idKecamatan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kelurahan.class);
    }

    @Override
    public Single<Token> getToken() {

        String credential = Credentials.basic("androkey", "androsecret");
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "clientauth")
                .addHeaders("Authorization", credential)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<Token> getTokenClient(JsonObject json) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/borrower_login")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<UserProfile> getUserLogin() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/profile")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);
    }


}
