package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AgentProfile implements Parcelable {

    @SerializedName("id")
    public Integer id;

    @SerializedName("created_time")
    public String createdTime;

    @SerializedName("updated_time")
    public String updatedTime;

    @SerializedName("deleted_time")
    public String deletedTime;

    @SerializedName("name")
    public String name;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("email")
    public String email;

    @SerializedName("phone")
    public String phone;

    @SerializedName("category")
    public String category;

    @SerializedName("agent_provider")
    public AgentProvider agentProvider;

    @SerializedName("banks")
    public List<Integer> banks = null;

    @SerializedName("status")
    public String status;

    @SerializedName("fcm_token")
    public String fcmToken;

    protected AgentProfile(Parcel in) {
        id = in.readInt();
        createdTime = in.readString();
        updatedTime = in.readString();
        deletedTime = in.readString();
        name = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
        category = in.readString();
        agentProvider = in.readParcelable(AgentProvider.class.getClassLoader());
        status = in.readString();
        fcmToken = in.readString();
    }

    public static final Creator<AgentProfile> CREATOR = new Creator<AgentProfile>() {
        @Override
        public AgentProfile createFromParcel(Parcel in) {
            return new AgentProfile(in);
        }

        @Override
        public AgentProfile[] newArray(int size) {
            return new AgentProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(createdTime);
        dest.writeString(updatedTime);
        dest.writeString(deletedTime);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(category);
        dest.writeParcelable(agentProvider, flags);
        dest.writeString(status);
        dest.writeString(fcmToken);
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

    public String getDeletedTime() {
        return deletedTime;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCategory() {
        return category;
    }

    public AgentProvider getAgentProvider() {
        return agentProvider;
    }

    public List<Integer> getBanks() {
        return banks;
    }

    public String getStatus() {
        return status;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
