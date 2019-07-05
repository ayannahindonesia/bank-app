package com.ayannah.bantenbank.data.model.Loans;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Loans{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("last_page")
	private int lastPage;

	@SerializedName("total_data")
	private int totalData;

	@SerializedName("from")
	private int from;

	@SerializedName("to")
	private int to;

	@SerializedName("rows")
	private int rows;

	@SerializedName("current_page")
	private int currentPage;

	public List<DataItem> getData(){
		return data;
	}

	public int getLastPage(){
		return lastPage;
	}

	public int getTotalData(){
		return totalData;
	}

	public int getFrom(){
		return from;
	}

	public int getTo(){
		return to;
	}

	public int getRows(){
		return rows;
	}

	public int getCurrentPage(){
		return currentPage;
	}
}