package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

public class CheckAccount {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
