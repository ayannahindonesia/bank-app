package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NasabahAgent {

    @SerializedName("total_data")
    private int totalData;

    @SerializedName("rows")
    private int rows;

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("last_page")
    private int last_page;

    @SerializedName("from")
    private int from;

    @SerializedName("to")
    private int to;

    @SerializedName("data")
    private List<UserProfile> data;

    public int getTotalData() {
        return totalData;
    }

    public int getRows() {
        return rows;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<UserProfile> getData() {
        return data;
    }
}
