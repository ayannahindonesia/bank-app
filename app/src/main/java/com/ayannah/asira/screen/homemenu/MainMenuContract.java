package com.ayannah.asira.screen.homemenu;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.MenuProduct;

import java.util.List;

public interface MainMenuContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadAllServiceMenu(List<BankService.Data> results);

        void showPromoAndNews(List<BeritaPromo> results);

        void displayUserIdentity(String name, String email);

        void showLogoutComplete();

        void showDataLoan(List<DataItem> items);

        void dismissDialog();
    }

    interface Presenter extends BasePresenter<View>{

        void getCurrentUserIdentity();

        void getMainMenu();

        void loadPromoAndNews();

        void notifLoanRequest();

        void logout();
    }
}
