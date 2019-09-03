package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;
import com.mobsandgeeks.saripaar.annotation.AssertFalse;

import java.util.List;

public class BankBaseResponse {

    @SerializedName("total_data")
    private int totalData;

    public int getTotalData() {
        return totalData;
    }
}
