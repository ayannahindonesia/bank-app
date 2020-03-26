package com.ayannah.asira.data.model.Loans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.ayannah.asira.data.model.FeesItem;
import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.data.model.UserProfile;
import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("id")
	private int id;

	@SerializedName("created_at")
	private String createdTime;

	@SerializedName("updated_at")
	private String updatedTime;

	@SerializedName("deleted_at")
	private String deletedTime;

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
	private float layawayPlan;

	@SerializedName("total_loan")
	private float totalLoan;

	@SerializedName("status")
	private String status;

	@SerializedName("product")
	private String product;

	@SerializedName("service")
	private String service;

	@SerializedName("disburse_date")
	private String disburseDate;

	@SerializedName("disburse_date_changed")
	private String disburseDateChanged;

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

	@SerializedName("approval_date")
	private String approvalDate;

	@SerializedName("payment_status")
	private String paymentStatus;

	@SerializedName("payment_note")
	private String paymentNote;

	@SerializedName("installment_details")
	private ArrayList<InstallmentDetails> installmentDetails;


	protected DataItem(Parcel in) {
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
		layawayPlan = in.readFloat();
		totalLoan = in.readFloat();
		id = in.readInt();
		deletedTime = in.readString();
		status = in.readString();
		product = in.readString();
		service = in.readString();
		disburseDate = in.readString();
		disburseDateChanged = in.readString();
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

	public String getDisburseDateChanged() {
		return disburseDateChanged;
	}

	public float getLayawayPlan(){
		return layawayPlan;
	}

	public float getTotalLoan(){
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

	public String getApprovalDate() {
		return approvalDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public String getPaymentNote() {
		return paymentNote;
	}

	public ArrayList<InstallmentDetails> getInstallmentDetails() {
		return installmentDetails;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
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
		dest.writeFloat(layawayPlan);
		dest.writeFloat(totalLoan);
		dest.writeInt(id);
		dest.writeString(deletedTime);
		dest.writeString(status);
		dest.writeString(product);
		dest.writeString(service);
		dest.writeString(disburseDate);
		dest.writeString(disburseDateChanged);
		dest.writeString(disburseStatus);
		dest.writeInt(disburseAmount);
		dest.writeString(rejectReason);
		dest.writeString(productName);
		dest.writeString(serviceName);
	}
}