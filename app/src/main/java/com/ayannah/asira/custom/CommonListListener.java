package com.ayannah.asira.custom;

import com.ayannah.asira.data.model.DummyLoanBorrower;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.UserBorrower;

public interface CommonListListener {

    interface LoanAdapterListener{

        void onClickItem(DataItem loans);
    }

    interface NotifAdapterListener {

        void onClickItem(String data);
    }

    interface ViewBorrowerListener{

        void onClickButton(UserBorrower user);

        void onClick(UserBorrower user);
    }

    interface ListLoanAgent{

        void onClickItem(DataItem loan);
    }

    interface CommonStringItemClickListener{

        void onClickItem(String item);
    }

}
