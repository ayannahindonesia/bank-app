package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FeesItem implements Parcelable {

	@SerializedName("amount")
	private String amount;

	@SerializedName("fee_method")
	private String feeMethod;

	@SerializedName("description")
	private String description;

	protected FeesItem(Parcel in) {
		amount = in.readString();
		feeMethod = in.readString();
		description = in.readString();
	}

	public static final Creator<FeesItem> CREATOR = new Creator<FeesItem>() {
		@Override
		public FeesItem createFromParcel(Parcel in) {
			return new FeesItem(in);
		}

		@Override
		public FeesItem[] newArray(int size) {
			return new FeesItem[size];
		}
	};

	public String getFeeMethod() {
		return feeMethod;
	}

	public void setFeeMethod(String feeMethod) {
		this.feeMethod = feeMethod;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(amount);
		dest.writeString(feeMethod);
		dest.writeString(description);
	}
}