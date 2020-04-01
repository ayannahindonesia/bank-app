package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InstallmentDetails implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted_at")
    private String deletedAt;

    @SerializedName("period")
    private int period;

    @SerializedName("loan_payment")
    private double loanPayment;

    @SerializedName("interest_payment")
    private double interesetPayment;

    @SerializedName("paid_date")
    private String paidDate;

    @SerializedName("paid_status")
    private boolean paidStatus;

    @SerializedName("paid_amount")
    private double paidAmount;

    @SerializedName("underpayment")
    private double underpayment;

    @SerializedName("penalty")
    private double penalty;

    @SerializedName("due_date")
    private String dueDate;

    @SerializedName("note")
    private String note;

    protected InstallmentDetails(Parcel in) {
        id = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
        period = in.readInt();
        loanPayment = in.readDouble();
        interesetPayment = in.readDouble();
        paidDate = in.readString();
        paidStatus = in.readByte() != 0;
        paidAmount = in.readDouble();
        underpayment = in.readDouble();
        penalty = in.readDouble();
        dueDate = in.readString();
        note = in.readString();
    }

    public static final Creator<InstallmentDetails> CREATOR = new Creator<InstallmentDetails>() {
        @Override
        public InstallmentDetails createFromParcel(Parcel in) {
            return new InstallmentDetails(in);
        }

        @Override
        public InstallmentDetails[] newArray(int size) {
            return new InstallmentDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public int getPeriod() {
        return period;
    }

    public double getLoanPayment() {
        return loanPayment;
    }

    public double getInteresetPayment() {
        return interesetPayment;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public boolean isPaidStatus() {
        return paidStatus;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getUnderpayment() {
        return underpayment;
    }

    public double getPenalty() {
        return penalty;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(deletedAt);
        dest.writeInt(period);
        dest.writeDouble(loanPayment);
        dest.writeDouble(interesetPayment);
        dest.writeString(paidDate);
        dest.writeByte((byte) (paidStatus ? 1 : 0));
        dest.writeDouble(paidAmount);
        dest.writeDouble(underpayment);
        dest.writeDouble(penalty);
        dest.writeString(dueDate);
        dest.writeString(note);
    }
}
