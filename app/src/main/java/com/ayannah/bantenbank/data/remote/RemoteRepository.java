package com.ayannah.bantenbank.data.remote;

import com.ayannah.bantenbank.data.model.Kabupaten;
import com.ayannah.bantenbank.data.model.Kecamatan;
import com.ayannah.bantenbank.data.model.Kelurahan;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.ayannah.bantenbank.data.model.Token;

import javax.inject.Singleton;

import io.reactivex.Single;

public interface RemoteRepository {

    Single<Provinsi> getProvinsi();

    Single<Kabupaten> getKabupaten(String idProvinsi);

    Single<Kecamatan> getKecamatan(String idKabupaten);

    Single<Kelurahan> getKelurahan(String idKecamatan);

    Single<Token> getToken();
}
