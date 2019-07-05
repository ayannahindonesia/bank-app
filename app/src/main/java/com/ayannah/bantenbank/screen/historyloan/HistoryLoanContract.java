package com.ayannah.bantenbank.screen.historyloan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.data.model.Loans.Loans;

import java.util.List;

public interface HistoryLoanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showAllTransaction(List<DataItem> results);
    }

    interface Presenter extends BasePresenter<View>{

        //1. load all history
        void loadHistoryTransaction();

        //2. sort history loan by name
        void  sortHistoryBy(String status);
    }
}
