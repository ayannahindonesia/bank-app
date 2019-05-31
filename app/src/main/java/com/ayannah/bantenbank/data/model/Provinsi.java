package com.ayannah.bantenbank.data.model;

import java.util.List;

public class Provinsi extends BaseResponse {

    private List<Data> semuaprovinsi;

    public List<Data> getSemuaprovinsi() {
        return semuaprovinsi;
    }

    public static class Data{

        private String id;
        private String nama;

        public String getId() {
            return id;
        }

        public String getNama() {
            return nama;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }
    }
}
