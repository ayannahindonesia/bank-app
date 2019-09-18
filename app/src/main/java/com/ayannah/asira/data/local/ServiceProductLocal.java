package com.ayannah.asira.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.data.model.Products;
import com.ayannah.asira.data.model.ServiceProducts;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

public class ServiceProductLocal implements ServiceProductInterface {

    private SharedPreferences mPreferences;

    private static final String PREF_TOTAL_DATA = "TOTAL_DATA";
    private static final String PREF_SERVICE_PRODUCTS = "SERVICE_PRODUCTS";

    @Inject
    public ServiceProductLocal(Context context){
        mPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
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
    public void setServiceProducts(List<Products> products) {
        Gson gson = new Gson();
        String json = gson.toJson(products);

        mPreferences.edit().putString(PREF_SERVICE_PRODUCTS, json).apply();
    }

    @Override
    public String getServiceProducts() {
        return mPreferences.getString(PREF_SERVICE_PRODUCTS, "");
    }

}
