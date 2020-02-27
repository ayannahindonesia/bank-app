package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Bank implements Parcelable {

	@SerializedName("Int64")
	private int int64;

	@SerializedName("Valid")
	private boolean valid;

	protected Bank(Parcel in) {
		int64 = in.readInt();
		valid = in.readByte() != 0;
	}

	public Bank() {

	}

	public static final Creator<Bank> CREATOR = new Creator<Bank>() {
		@Override
		public Bank createFromParcel(Parcel in) {
			return new Bank(in);
		}

		@Override
		public Bank[] newArray(int size) {
			return new Bank[size];
		}
	};

	public int getInt64(){
		return int64;
	}

	public boolean isValid(){
		return valid;
	}

	public void setInt64(int int64) {
		this.int64 = int64;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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