package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckBorrower {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    //    @SerializedName("id_agent_borrower")
//    private int id_agent_borrower;
//
//    @SerializedName("status")
//    private boolean status;
//
//    @SerializedName("fields")
//    private List<String> fields;
//
//    public int getId_agent_borrower() {
//        return id_agent_borrower;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public List<String> getFields() {
//        return fields;
//    }
//
//    public void setFields(List<String> financingSector) {
//        this.fields = financingSector;
//    }
}
