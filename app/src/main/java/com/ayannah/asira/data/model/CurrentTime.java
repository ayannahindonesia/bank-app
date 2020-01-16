package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class CurrentTime {

    @SerializedName("time")
    private String time;

    @SerializedName("stacks")
    private String stacks;

    public String getTime() {
        return time;
    }

    public String getStacks() {
        return stacks;
    }
}
