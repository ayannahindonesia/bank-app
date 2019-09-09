package com.ayannah.asira.screen.historyloan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Loans.DataItem;

import java.util.List;

public interface HistoryLoanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showAllTransaction(List<DataItem> results);
    }

    interface Presenter extends BasePresenter<View>{

        //1. load all history
        void loadHistoryTransaction(String sortWithStatus);

    }
}
