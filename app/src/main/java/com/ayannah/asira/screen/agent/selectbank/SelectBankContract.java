package com.ayannah.asira.screen.agent.selectbank;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankTypeDummy;

import java.util.List;

public interface SelectBankContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showBanks(List<BankTypeDummy> results);
    }

    interface Presenter extends BasePresenter<View>{

        void getBanks();
    }
}
