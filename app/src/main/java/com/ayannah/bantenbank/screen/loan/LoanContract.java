package com.ayannah.bantenbank.screen.loan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface LoanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);
    }

    interface Presenter extends BasePresenter<View>{


    }
}
