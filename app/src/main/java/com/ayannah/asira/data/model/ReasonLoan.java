package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReasonLoan extends BaseCountResponse {

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

        @SerializedName("deleted_time")
        private String deteledTime;

        @SerializedName("name")
        private String name;

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

        public String getDeteledTime() {
            return deteledTime;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }
    }


}
