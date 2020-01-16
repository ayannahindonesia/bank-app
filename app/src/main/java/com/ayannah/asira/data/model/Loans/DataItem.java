package com.ayannah.asira.data.model.Loans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import com.ayannah.asira.data.model.FeesItem;
import com.ayannah.asira.data.model.UserProfile;
import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("owner")
	private Owner owner;

	@SerializedName("created_at")
	private String createdTime;

	@SerializedName("updated_at")
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

	@SerializedName("deleted_at")
	private String deletedTime;

	@SerializedName("status")
	private String status;

	@SerializedName("product")
	private String product;

	@SerializedName("service")
	private String service;

	@SerializedName("disburse_date")
	private String disburseDate;

	@SerializedName("disburse_status")
	private String disburseStatus;

	@SerializedName("disburse_amount")
	private int disburseAmount;

	@SerializedName("reject_reason")
	private String rejectReason;

	@SerializedName("product_name")
	private String productName;

	@SerializedName("service_name")
	private String serviceName;

	protected DataItem(Parcel in) {
		owner = in.readParcelable(Owner.class.getClassLoader());
		createdTime = in.readString();
		updatedTime = in.readString();
		fees = in.createTypedArrayList(FeesItem.CREATOR);
		loanIntention = in.readString();
		dueDate = in.readString();
		intentionDetails = in.readString();
		loanAmount = in.readInt();
		otpVerified = in.readByte() != 0;
		interest = in.readDouble();
		borrowerInfo = in.readParcelable(UserProfile.class.getClassLoader());
		installment = in.readInt();
		layawayPlan = in.readDouble();
		totalLoan = in.readInt();
		id = in.readInt();
		deletedTime = in.readString();
		status = in.readString();
		product = in.readString();
		service = in.readString();
		disburseDate = in.readString();
		disburseStatus = in.readString();
		disburseAmount = in.readInt();
		rejectReason = in.readString();
		productName = in.readString();
		serviceName = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

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

	public String getDisburseDate() {
		return disburseDate;
	}

	public String getDisburseStatus() {
		return disburseStatus;
	}

	public int getDisburseAmount() {
		return disburseAmount;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public String getProductName() {
		return productName;
	}

	public String getServiceName() {
		return serviceName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(owner, flags);
		dest.writeString(createdTime);
		dest.writeString(updatedTime);
		dest.writeTypedList(fees);
		dest.writeString(loanIntention);
		dest.writeString(dueDate);
		dest.writeString(intentionDetails);
		dest.writeInt(loanAmount);
		dest.writeByte((byte) (otpVerified ? 1 : 0));
		dest.writeDouble(interest);
		dest.writeParcelable(borrowerInfo, flags);
		dest.writeInt(installment);
		dest.writeDouble(layawayPlan);
		dest.writeInt(totalLoan);
		dest.writeInt(id);
		dest.writeString(deletedTime);
		dest.writeString(status);
		dest.writeString(product);
		dest.writeString(service);
		dest.writeString(disburseDate);
		dest.writeString(disburseStatus);
		dest.writeInt(disburseAmount);
		dest.writeString(rejectReason);
		dest.writeString(productName);
		dest.writeString(serviceName);
	}
}