package com.ayannah.bantenbank.screen.homemenu;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.BeritaPromo;
import com.ayannah.bantenbank.data.model.Loans;

import java.util.List;

public interface MainMenuContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showMainMenu();

        void showPromoAndNews(List<BeritaPromo> results);

        void showLoandHistory(List<Loans> results);

    }

    interface Presenter extends BasePresenter<View>{

        void getMainMenu();

        void checkLoginStatus();

        void loadPromoAndNews();

        void loadLoanhistory();
    }
}
