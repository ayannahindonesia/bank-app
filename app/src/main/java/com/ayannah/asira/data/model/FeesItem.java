package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class FeesItem{

	@SerializedName("amount")
	private String amount;

	@SerializedName("description")
	private String description;

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
}