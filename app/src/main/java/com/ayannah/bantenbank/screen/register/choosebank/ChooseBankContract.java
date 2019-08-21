package com.ayannah.bantenbank.screen.register.choosebank;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.BankList;

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
