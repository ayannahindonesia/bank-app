package com.ayannah.asira.data.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BankList implements Serializable
{

    @SerializedName("total_data")
    private Integer totalData;

    @SerializedName("rows")
    private Integer rows;

    @SerializedName("current_page")
    private Integer currentPage;

    @SerializedName("last_page")
    private Integer lastPage;

    @SerializedName("from")
    private Integer from;

    @SerializedName("to")
    private Integer to;

    @SerializedName("data")
    private List<BankDetail> data = null;

    public Integer getTotalData() {
        return totalData;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public List<BankDetail> getData() {
        return data;
    }

    public void setData(List<BankDetail> data) {
        this.data = data;
    }

}