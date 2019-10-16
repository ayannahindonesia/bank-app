package com.ayannah.asira.data.model.Loans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Owner implements Parcelable {

	@SerializedName("Int64")
	private int int64;

	@SerializedName("Valid")
	private boolean valid;

	protected Owner(Parcel in) {
		int64 = in.readInt();
		valid = in.readByte() != 0;
	}

	public static final Creator<Owner> CREATOR = new Creator<Owner>() {
		@Override
		public Owner createFromParcel(Parcel in) {
			return new Owner(in);
		}

		@Override
		public Owner[] newArray(int size) {
			return new Owner[size];
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