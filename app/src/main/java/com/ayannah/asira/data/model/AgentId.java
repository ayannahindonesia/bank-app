package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class AgentId {

    @SerializedName("Int64")
    private int Int64;

    @SerializedName("Valid")
    private boolean valid;

    public int getInt64() {
        return Int64;
    }

    public boolean isValid() {
        return valid;
    }
}
