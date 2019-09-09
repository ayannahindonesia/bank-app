package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("expires_in")
    long expiresIn;

    @SerializedName("token")
    String token;

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getToken() {
        return token;
    }
}
