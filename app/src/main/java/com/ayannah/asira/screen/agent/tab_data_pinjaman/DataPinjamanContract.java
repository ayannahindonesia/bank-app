package com.ayannah.asira.screen.agent.tab_data_pinjaman;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankList;

public interface DataPinjamanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int errorCode);

        void showBanks(BankList bankList);
    }

    interface Presenter extends BasePresenter<View>{

        void fetchBanks();

    }
}
