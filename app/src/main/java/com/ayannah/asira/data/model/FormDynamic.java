package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FormDynamic implements Parcelable {

    @SerializedName("type")
    String type;

    @SerializedName("label")
    String label;

    @SerializedName("value")
    String value;

    @SerializedName("status")
    String status;

    String answers;

    protected FormDynamic(Parcel in) {
        type = in.readString();
        label = in.readString();
        value = in.readString();
        status = in.readString();
        answers = in.readString();
    }

    public static final Creator<FormDynamic> CREATOR = new Creator<FormDynamic>() {
        @Override
        public FormDynamic createFromParcel(Parcel in) {
            return new FormDynamic(in);
        }

        @Override
        public FormDynamic[] newArray(int size) {
            return new FormDynamic[size];
        }
    };

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(label);
        dest.writeString(value);
        dest.writeString(status);
        dest.writeString(answers);
    }
}
