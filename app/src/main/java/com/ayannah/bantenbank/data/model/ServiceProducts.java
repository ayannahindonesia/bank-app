package com.ayannah.bantenbank.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceProducts{

	@SerializedName("data")
	private List<Products> products;

	@SerializedName("total_data")
	private int totalData;

	public void setProducts(List<Products> products){
		this.products = products;
	}

	public List<Products> getProducts(){
		return products;
	}

	public void setTotalData(int totalData){
		this.totalData = totalData;
	}

	public int getTotalData(){
		return totalData;
	}
}