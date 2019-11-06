package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class FCMTokenResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("rows")
    private boolean rows;

    public String getMessage() {
        return message;
    }

    public boolean isRows() {
        return rows;
    }
}
