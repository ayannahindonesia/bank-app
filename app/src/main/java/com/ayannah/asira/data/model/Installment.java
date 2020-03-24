package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Installment implements Parcelable {

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
    private int loanPayment;

    @SerializedName("interest_payment")
    private int interesetPayment;

    @SerializedName("paid_date")
    private String paidDate;

    @SerializedName("paid_status")
    private boolean paidStatus;

    @SerializedName("paid_amount")
    private int paidAmount;

    @SerializedName("underpayment")
    private int underpayment;

    @SerializedName("penalty")
    private int penalty;

    @SerializedName("due_date")
    private String dueDate;

    @SerializedName("note")
    private String note;

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

    public int getLoanPayment() {
        return loanPayment;
    }

    public int getInteresetPayment() {
        return interesetPayment;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public boolean isPaidStatus() {
        return paidStatus;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getUnderpayment() {
        return underpayment;
    }

    public int getPenalty() {
        return penalty;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getNote() {
        return note;
    }

    private Installment(Parcel in){
        id = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
        period = in.readInt();
        loanPayment = in.readInt();
        interesetPayment = in.readInt();
        paidDate = in.readString();
        paidStatus = in.readByte() != 0;
        paidAmount = in.readInt();
        underpayment = in.readInt();
        penalty = in.readInt();
        dueDate = in.readString();
        note = in.readString();
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
        dest.writeInt(loanPayment);
        dest.writeInt(interesetPayment);
        dest.writeString(paidDate);
        dest.writeByte((byte) (paidStatus ? 1 : 0));
        dest.writeInt(paidAmount);
        dest.writeInt(underpayment);
        dest.writeInt(penalty);
        dest.writeString(dueDate);
        dest.writeString(note);
    }


    public static final Creator<Installment> CREATOR = new Creator<Installment>() {
        @Override
        public Installment createFromParcel(Parcel in) {
            return new Installment(in);
        }

        @Override
        public Installment[] newArray(int size) {
            return new Installment[size];
        }
    };
}
