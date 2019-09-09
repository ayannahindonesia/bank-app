package com.ayannah.asira.screen.earninginfo;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface EarningContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadPenghasilan(String pendapatan, String pendapatanLain, String sumberLain);

        void completeUpdateIncome();

    }

    interface Presenter extends BasePresenter<View>{

        void getPenghasilan();

        void updateUserIncome(int primaryIncome, int secondaryIncome, String otherIncomeSource);

    }
}
