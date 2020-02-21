package com.ayannah.asira.screen.agent.tab_data_pinjaman.filter;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankDetail;

import java.util.List;

public interface FilterPinjamanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int code);

        void getAllBanks(List<BankDetail> results);

    }

    interface Presenter extends BasePresenter<View>{

        void retrieveBanks();

    }
}
