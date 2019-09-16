package com.ayannah.asira.data.model.Loans;

import java.util.List;

import com.ayannah.asira.data.model.UserProfile;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("owner")
	private Owner owner;

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("updated_time")
	private String updatedTime;

	@SerializedName("fees")
	private List<FeesItem> fees;

	@SerializedName("loan_intention")
	private String loanIntention;

	@SerializedName("due_date")
	private String dueDate;

	@SerializedName("intention_details")
	private String intentionDetails;

	@SerializedName("loan_amount")
	private int loanAmount;

	@SerializedName("otp_verified")
	private boolean otpVerified;

	@SerializedName("interest")
	private double interest;

	@SerializedName("borrower_info")
	private UserProfile borrowerInfo;

	@SerializedName("installment")
	private int installment;

	@SerializedName("layaway_plan")
	private double layawayPlan;

	@SerializedName("total_loan")
	private int totalLoan;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_time")
	private String deletedTime;

	@SerializedName("status")
	private String status;

	@SerializedName("product")
	private String product;

	@SerializedName("service")
	private String service;

	public Owner getOwner(){
		return owner;
	}

	public String getCreatedTime(){
		return createdTime;
	}

	public String getUpdatedTime(){
		return updatedTime;
	}

	public List<FeesItem> getFees(){
		return fees;
	}

	public String getLoanIntention(){
		return loanIntention;
	}

	public String getDueDate(){
		return dueDate;
	}

	public String getIntentionDetails(){
		return intentionDetails;
	}

	public int getLoanAmount(){
		return loanAmount;
	}

	public boolean isOtpVerified(){
		return otpVerified;
	}

	public double getInterest(){
		return interest;
	}

	public UserProfile getBorrowerInfo(){
		return borrowerInfo;
	}

	public int getInstallment(){
		return installment;
	}

	public double getLayawayPlan(){
		return layawayPlan;
	}

	public int getTotalLoan(){
		return totalLoan;
	}

	public int getId(){
		return id;
	}

	public String getDeletedTime(){
		return deletedTime;
	}

	public String getStatus(){
		return status;
	}

	public String getProduct() {
		return product;
	}

	public String getService() {
		return service;
	}
}