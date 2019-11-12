package com.ayannah.asira.screen.agent.registerborrower.choosebank;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankList;

public interface ChooseBankAgentContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void successGetAllBanks(BankList bankList);
    }

    interface Presenter extends BasePresenter<View> {

        void getAllBanks();

        void getPublicToken();
    }
}
