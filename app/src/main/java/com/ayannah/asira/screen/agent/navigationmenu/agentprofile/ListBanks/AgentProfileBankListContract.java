package com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankList;

public interface AgentProfileBankListContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String err);

        void successGetAllBanks(BankList response);
    }

    interface Presenter extends BasePresenter<View> {

        void getAllBanks();
    }
}
