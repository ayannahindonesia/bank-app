package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class OTPLoanResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
