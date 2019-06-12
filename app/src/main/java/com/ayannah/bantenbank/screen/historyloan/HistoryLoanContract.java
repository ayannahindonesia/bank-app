package com.ayannah.bantenbank.screen.historyloan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.Loans;

import java.util.List;

public interface HistoryLoanContract {

    interface View extends BaseView<Presenter>{

        void showAllTransaction(List<Loans> results);
    }

    interface Presenter extends BasePresenter<View>{

        void loadHistoryTransaction();

    }
}
