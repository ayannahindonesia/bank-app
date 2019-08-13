package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

public class TaxIDImage {

    @SerializedName("Int64")
    private int int64;

    @SerializedName("Valid")
    private boolean valid;

    public int getInt64(){
        return int64;
    }

    public boolean isValid(){
        return valid;
    }
}
