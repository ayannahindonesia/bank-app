package com.ayannah.asira.screen.borrower.tab_historyloan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Loans.DataItem;

import java.util.List;

public interface HistoryLoanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int code);

        void showAllTransaction(List<DataItem> results);
    }

    interface Presenter extends BasePresenter<View>{

        //1. load all history
        void loadHistoryTransaction(String sortWithStatus);

        void getProducts();

        void requestOTPPersonal(boolean isPersonal, int coba);
    }
}
