package com.ayannah.bantenbank.data.model.Loans;

import com.google.gson.annotations.SerializedName;

public class FeesItem{

	@SerializedName("amount")
	private int amount;

	@SerializedName("description")
	private String description;

	public int getAmount(){
		return amount;
	}

	public String getDescription(){
		return description;
	}
}