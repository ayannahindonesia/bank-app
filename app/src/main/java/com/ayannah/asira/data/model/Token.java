package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("expires_in")
    long expiresIn;

    @SerializedName("token")
    String token;

    @SerializedName("message")
    String message;

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
