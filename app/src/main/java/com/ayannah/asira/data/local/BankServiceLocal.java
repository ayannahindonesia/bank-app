package com.ayannah.asira.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.data.model.BankService;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

public class BankServiceLocal implements BankServiceInterface {

    private SharedPreferences mPreferences;

    private static final String PREF_TOTAL_DATA = "TOTAL_DATA";
    private static final String PREF_BANK_SERVICE = "BANK_SERVICE";

    @Inject
    public BankServiceLocal(Context application){
        mPreferences = application.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Override
    public void setTotalData(int totalData) {
        mPreferences.edit().putInt(PREF_TOTAL_DATA, totalData).apply();
    }

    @Override
    public int getTotalData() {
        return mPreferences.getInt(PREF_TOTAL_DATA, 0);
    }

    @Override
    public void setBankService(List<BankService.Data> bankService) {
        Gson gson = new Gson();
        String json = gson.toJson(bankService);

        mPreferences.edit().putString(PREF_BANK_SERVICE, json).apply();
    }

    @Override
    public String getBankService() {
        return mPreferences.getString(PREF_BANK_SERVICE, "");
    }


}
