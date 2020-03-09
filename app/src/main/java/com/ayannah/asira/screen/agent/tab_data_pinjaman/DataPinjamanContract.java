package com.ayannah.asira.screen.agent.tab_data_pinjaman;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.Loans.Loans;

import java.util.List;

public interface DataPinjamanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int errorCode);

        void showAllLoans(List<DataItem> results);
    }

    interface Presenter extends BasePresenter<View>{

        void retrieveLoans(String bank, String status, String name);

    }
}
