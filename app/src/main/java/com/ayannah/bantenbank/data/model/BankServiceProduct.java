package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankServiceProduct extends BankBaseResponse {

    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data{

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("min_timespan")
        private int minTimeSpan;

        @SerializedName("max_timespan")
        private int maxTimeSpan;

        @SerializedName("interest")
        private int interest;

        @SerializedName("min_loan")
        private int minLoan;

        @SerializedName("max_loan")
        private int maxLoan;

        @SerializedName("asn_fee")
        private String asnFee;

        @SerializedName("service")
        private int service;

        @SerializedName("assurance")
        private String assurance;

        @SerializedName("status")
        private String status;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getMinTimeSpan() {
            return minTimeSpan;
        }

        public int getMaxTimeSpan() {
            return maxTimeSpan;
        }

        public int getInterest() {
            return interest;
        }

        public int getMinLoan() {
            return minLoan;
        }

        public int getMaxLoan() {
            return maxLoan;
        }

        public String getAsnFee() {
            return asnFee;
        }

        public int getService() {
            return service;
        }

        public String getAssurance() {
            return assurance;
        }

        public String getStatus() {
            return status;
        }
    }
}
