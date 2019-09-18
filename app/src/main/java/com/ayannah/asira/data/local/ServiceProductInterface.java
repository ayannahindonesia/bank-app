package com.ayannah.asira.data.local;

import com.ayannah.asira.data.model.Products;

import java.util.List;

public interface ServiceProductInterface {
    void setTotalData(int totalData);

    int getTotalData();

    void setServiceProducts(List<Products> products);

    String getServiceProducts();
}
