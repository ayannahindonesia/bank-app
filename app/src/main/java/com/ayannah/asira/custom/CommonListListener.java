package com.ayannah.asira.custom;

import com.ayannah.asira.data.model.Loans.DataItem;

public interface CommonListListener {

    interface LoanAdapterListener{

        void onClickItem(DataItem loans);
    }

    interface NotifAdapterListener {

        void onClickItem(String data);
    }
}
