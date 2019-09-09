package com.ayannah.asira.screen.register.choosebank;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankList;

public interface ChooseBankContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void successGetAllBanks(BankList response);
    }

    interface Presenter extends BasePresenter<View>{

        void loadBank();

        void getAllBanks();

        void getPublicToken();
    }
}
