package com.ayannah.asira.data.model;

import java.util.ArrayList;

public class Angsuran {

    private String page;
    private ArrayList<InstallmentDetails> data;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<InstallmentDetails> getData() {
        return data;
    }


    public void setData(ArrayList<InstallmentDetails> data) {
        this.data = data;
    }
}
