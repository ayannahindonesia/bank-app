package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class BaseCountResponse {

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

}
