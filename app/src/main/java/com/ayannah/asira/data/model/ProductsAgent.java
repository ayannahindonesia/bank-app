package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsAgent {

	@SerializedName("id")
	private Integer id;

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("updated_time")
	private String updatedTime;

	@SerializedName("deleted_time")
	private String deletedTime;

	@SerializedName("name")
	private String name;

	@SerializedName("service_id")
	private Integer serviceId;

	@SerializedName("min_timespan")
	private Integer minTimespan;

	@SerializedName("max_timespan")
	private Integer maxTimespan;

	@SerializedName("interest")
	private Integer interest;

	@SerializedName("min_loan")
	private Integer minLoan;

	@SerializedName("max_loan")
	private Integer maxLoan;

	@SerializedName("fees")
	private List<FeesItem> fees = null;

	@SerializedName("collaterals")
	private List<String> collaterals = null;

	@SerializedName("financing_sector")
	private List<String> financingSector = null;

	@SerializedName("assurance")
	private String assurance;

	@SerializedName("status")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(String deletedTime) {
		this.deletedTime = deletedTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getMinTimespan() {
		return minTimespan;
	}

	public void setMinTimespan(Integer minTimespan) {
		this.minTimespan = minTimespan;
	}

	public Integer getMaxTimespan() {
		return maxTimespan;
	}

	public void setMaxTimespan(Integer maxTimespan) {
		this.maxTimespan = maxTimespan;
	}

	public Integer getInterest() {
		return interest;
	}

	public void setInterest(Integer interest) {
		this.interest = interest;
	}

	public Integer getMinLoan() {
		return minLoan;
	}

	public void setMinLoan(Integer minLoan) {
		this.minLoan = minLoan;
	}

	public Integer getMaxLoan() {
		return maxLoan;
	}

	public void setMaxLoan(Integer maxLoan) {
		this.maxLoan = maxLoan;
	}

	public List<FeesItem> getFees() {
		return fees;
	}

	public void setFees(List<FeesItem> fees) {
		this.fees = fees;
	}

	public List<String> getCollaterals() {
		return collaterals;
	}

	public void setCollaterals(List<String> collaterals) {
		this.collaterals = collaterals;
	}

	public List<String> getFinancingSector() {
		return financingSector;
	}

	public void setFinancingSector(List<String> financingSector) {
		this.financingSector = financingSector;
	}

	public String getAssurance() {
		return assurance;
	}

	public void setAssurance(String assurance) {
		this.assurance = assurance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}