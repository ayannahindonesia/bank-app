package com.ayannah.asira.screen.agent.services;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.BeritaPromo;

import java.util.List;

public interface ListServicesAgentContract {
    interface View extends BaseView<Presenter> {

        void setAllServices(List<BankService.Data> datas);

        void showPromoAndNews(List<BeritaPromo> listBeritaPromo);

        void showErrorMessage(String err);
    }

    interface Presenter extends BasePresenter<View> {

        void getAllService(String bank_id);

        void loadPromoAndNews();
    }
}
