package com.ayannah.bantenbank.screen.homemenu;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.BeritaPromo;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.data.model.Loans.Loans;
import com.ayannah.bantenbank.data.model.MenuProduct;

import java.util.List;

public interface MainMenuContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showMainMenu(List<MenuProduct> results);

        void showPromoAndNews(List<BeritaPromo> results);

        void displayUserIdentity(String name, String email);

        void showLogoutComplete();

        void showDataLoan(List<DataItem> items);

    }

    interface Presenter extends BasePresenter<View>{

        void getCurrentUserIdentity();

        void getMainMenu();

        void loadPromoAndNews();

        void notifLoanRequest();

        void logout();
    }
}
