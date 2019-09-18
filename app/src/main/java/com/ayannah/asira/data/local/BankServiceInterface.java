package com.ayannah.asira.data.local;

import com.ayannah.asira.data.model.BankService;

import java.util.List;

public interface BankServiceInterface {

    void setTotalData(int totalData);

    int getTotalData();

    void setBankService(List<BankService.Data> bankService);

    String getBankService();

}
