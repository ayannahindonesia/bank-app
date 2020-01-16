package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AgentProviderDetail implements Parcelable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("updated_time")
    private String updatedTime;

    @SerializedName("name")
    private String name;

    @SerializedName("pic")
    private String pic;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("status")
    private String status;

    protected AgentProviderDetail(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        createdTime = in.readString();
        updatedTime = in.readString();
        name = in.readString();
        pic = in.readString();
        phone = in.readString();
        address = in.readString();
        status = in.readString();
    }

    public static final Creator<AgentProviderDetail> CREATOR = new Creator<AgentProviderDetail>() {
        @Override
        public AgentProviderDetail createFromParcel(Parcel in) {
            return new AgentProviderDetail(in);
        }

        @Override
        public AgentProviderDetail[] newArray(int size) {
            return new AgentProviderDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(createdTime);
        dest.writeString(updatedTime);
        dest.writeString(name);
        dest.writeString(pic);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(status);
    }

    public Integer getId() {
        return id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getName() {
        return name;
    }

    public String getPic() {
        return pic;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
