package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("error")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
