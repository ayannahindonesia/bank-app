package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Provinsi extends BaseResponse {

    @SerializedName("semuaprovinsi")
    private List<Data> semuaprovinsi;

    public List<Data> getSemuaprovinsi() {
        return semuaprovinsi;
    }

    public static class Data{

        @SerializedName("id")
        private String id;

        @SerializedName("nama")
        private String nama;

        public String getId() {
            return id;
        }

        public String getNama() {
            return nama;
        }

    }
}
