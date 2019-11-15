package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AgentProvider implements Parcelable {

	@SerializedName("Int64")
	private int int64;

	@SerializedName("Valid")
	private boolean valid;

	protected AgentProvider(Parcel in) {
		int64 = in.readInt();
		valid = in.readByte() != 0;
	}

	public static final Creator<AgentProvider> CREATOR = new Creator<AgentProvider>() {
		@Override
		public AgentProvider createFromParcel(Parcel in) {
			return new AgentProvider(in);
		}

		@Override
		public AgentProvider[] newArray(int size) {
			return new AgentProvider[size];
		}
	};

	public int getInt64(){
		return int64;
	}

	public boolean isValid(){
		return valid;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(int64);
		dest.writeByte((byte) (valid ? 1 : 0));
	}
}