package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("total_data")
    private int totalData;

    @SerializedName("rows")
    private int rows;

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("last_page")
    private int lastPage;

    @SerializedName("from")
    private int from;

    @SerializedName("to")
    private int to;

    @SerializedName("data")
    private List<Data> data;

    public int getTotalData() {
        return totalData;
    }

    public int getRows() {
        return rows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data{

        @SerializedName("id")
        private int id;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("deleted_at")
        private String deletedAt;

        @SerializedName("title")
        private String title;

        @SerializedName("description")
        private String description;

        public int getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

}
