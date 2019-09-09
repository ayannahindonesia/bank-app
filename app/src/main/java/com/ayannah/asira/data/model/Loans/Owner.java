package com.ayannah.asira.data.model.Loans;

import com.google.gson.annotations.SerializedName;

public class Owner{

	@SerializedName("Int64")
	private int int64;

	@SerializedName("Valid")
	private boolean valid;

	public int getInt64(){
		return int64;
	}

	public boolean isValid(){
		return valid;
	}
}