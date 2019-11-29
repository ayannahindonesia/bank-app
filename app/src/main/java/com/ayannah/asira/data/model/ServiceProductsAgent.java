package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceProductsAgent {

	@SerializedName("data")
	private List<ProductsAgent> products;

	@SerializedName("total_data")
	private int totalData;

	public void setProducts(List<ProductsAgent> products){
		this.products = products;
	}

	public List<ProductsAgent> getProducts(){
		return products;
	}

	public void setTotalData(int totalData){
		this.totalData = totalData;
	}

	public int getTotalData(){
		return totalData;
	}
}