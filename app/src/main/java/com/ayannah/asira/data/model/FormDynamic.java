package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class FormDynamic {

    @SerializedName("type")
    String type;

    @SerializedName("label")
    String label;

    @SerializedName("values")
    String[] value;

    @SerializedName("status")
    String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }


    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
