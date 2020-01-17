package com.ayannah.asira.screen.borrower.tab_beranda;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.data.model.Loans.DataItem;

import java.util.List;

public interface MainMenuContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int code);

        void loadAllServiceMenu(List<BankService.Data> results);

        void showPromoAndNews(List<BeritaPromo> results);

        void showDataLoan(List<DataItem> items);

        void showUserData(String name, String token);

        void successGetPublicTokenLender();

        void successGetCurrentTime(String time);

        void showTopUpTagihanMenu(List<String> results);

        void setLoanStatus(String loanStatus);
    }

    interface Presenter extends BasePresenter<View>{

        void getTokenLender();

        void getMainMenu();

        void loadPromoAndNews();

        void fetchTopupTagihan();

        void notifLoanRequest();

        void getTokenAdminLender();

        void getCurrentTime();

        void getProfile();
    }
}
