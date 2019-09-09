package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class BankBaseResponse {

    @SerializedName("total_data")
    private int totalData;

    public int getTotalData() {
        return totalData;
    }
}
