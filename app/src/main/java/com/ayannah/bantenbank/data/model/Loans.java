package com.ayannah.bantenbank.data.model;

public class Loans {
    private int noLoan;
    private String loanType;
    private int amount;
    private String status;

    public int getNoLoan() {
        return noLoan;
    }

    public void setNoLoan(int noLoan) {
        this.noLoan = noLoan;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
