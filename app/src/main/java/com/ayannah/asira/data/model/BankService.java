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

        public String getStatus() {
            return status;
        }
    }
}
