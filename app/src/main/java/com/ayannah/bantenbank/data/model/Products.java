package com.ayannah.bantenbank.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Products {

	@SerializedName("fees")
	private List<FeesItem> fees;

	@SerializedName("min_timespan")
	private int minTimespan;

	@SerializedName("max_loan")
	private int maxLoan;

	@SerializedName("collaterals")
	private List<String> collaterals;

	@SerializedName("max_timespan")
	private int maxTimespan;

	@SerializedName("min_loan")
	private int minLoan;

	@SerializedName("financing_sector")
	private List<String> financingSector;

	@SerializedName("assurance")
	private String assurance;

	@SerializedName("interest")
	private int interest;

	@SerializedName("service")
	private int service;

	@SerializedName("asn_fee")
	private String asnFee;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public List<FeesItem> getFees() {
		return fees;
	}

	public void setFees(List<FeesItem> fees) {
		this.fees = fees;
	}

	public void setMinTimespan(int minTimespan){
		this.minTimespan = minTimespan;
	}

	public int getMinTimespan(){
		return minTimespan;
	}

	public void setMaxLoan(int maxLoan){
		this.maxLoan = maxLoan;
	}

	public int getMaxLoan(){
		return maxLoan;
	}

	public void setCollaterals(List<String> collaterals){
		this.collaterals = collaterals;
	}

	public List<String> getCollaterals(){
		return collaterals;
	}

	public void setMaxTimespan(int maxTimespan){
		this.maxTimespan = maxTimespan;
	}

	public int getMaxTimespan(){
		return maxTimespan;
	}

	public void setMinLoan(int minLoan){
		this.minLoan = minLoan;
	}

	public int getMinLoan(){
		return minLoan;
	}

	public void setFinancingSector(List<String> financingSector){
		this.financingSector = financingSector;
	}

	public List<String> getFinancingSector(){
		return financingSector;
	}

	public void setAssurance(String assurance){
		this.assurance = assurance;
	}

	public String getAssurance(){
		return assurance;
	}

	public void setInterest(int interest){
		this.interest = interest;
	}

	public int getInterest(){
		return interest;
	}

	public void setService(int service){
		this.service = service;
	}

	public int getService(){
		return service;
	}

	public void setAsnFee(String asnFee){
		this.asnFee = asnFee;
	}

	public String getAsnFee(){
		return asnFee;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}