package com.ayannah.asira.data.model;

import java.util.List;

public class BankTypeDummy {

    private String type;
    private List<BankDummy> banks;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BankDummy> getBanks() {
        return banks;
    }

    public void setBanks(List<BankDummy> banks) {
        this.banks = banks;
    }
}
