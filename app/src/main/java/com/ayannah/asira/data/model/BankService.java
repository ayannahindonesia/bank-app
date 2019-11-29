package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankService extends BankBaseResponse {

    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data{

        @SerializedName("id")
        private int id;

        @SerializedName("created_time")
        private String createdTime;

        @SerializedName("updated_time")
        private String updatedTime;

        @SerializedName("name")
        private String name;

        @SerializedName("bank_id")
        private int bankId;

        @SerializedName("image_id")
        private int imageId;

        @SerializedName("status")
        private String status;

        public int getId() {
            return id;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public String getName() {
            return name;
        }

        public int getImageId() {
            return imageId;
        }

        public int getBankId() {
            return bankId;
        }

        public String getStatus() {
            return status;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
