package com.ayannah.bantenbank.data.remote;

import com.ayannah.bantenbank.data.model.Provinsi;

import io.reactivex.Single;

public interface RemoteRepository {

    Single<Provinsi> getProvinsi();

}
