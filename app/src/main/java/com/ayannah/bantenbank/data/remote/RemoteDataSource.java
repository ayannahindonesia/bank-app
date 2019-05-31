package com.ayannah.bantenbank.data.remote;

import com.androidnetworking.common.Priority;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.Single;

public class RemoteDataSource implements RemoteRepository {


    @Override
    public Single<Provinsi> getProvinsi() {
        return Rx2AndroidNetworking.get("http://dev.farizdotid.com/api/daerahindonesia/provinsi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Provinsi.class);

    }


}
