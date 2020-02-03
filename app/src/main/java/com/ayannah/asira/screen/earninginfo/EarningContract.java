package com.ayannah.asira.screen.earninginfo;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.UserProfile;

public interface EarningContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadPenghasilan(String pendapatan, String pendapatanLain, String sumberLain);

        void completeUpdateIncome(UserProfile userProfile);

        void getBorrowerIncomeDetail(int primaryIncome, int secondaryIncome, String otherIncome);

    }

    interface Presenter extends BasePresenter<View>{

        void getPenghasilan();

        void updateUserIncome(int primaryIncome, int secondaryIncome, String otherIncomeSource);

        void updateUserIncomeFromAgent(int primary, int secondary, String others, String borrowerID);

        void retrieveBorrowerIncomeDetail();
    }
}
