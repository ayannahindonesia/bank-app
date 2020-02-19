package com.ayannah.asira.screen.agent.tab_beranda;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.MenuAgent;
import com.ayannah.asira.data.model.UserBorrower;

import java.util.List;

public interface BerandaContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int code);

        void showUserAttributes(String name, String phone);

        void showRecentAgent(List<UserBorrower> userBorrowers);

        void goToOTPInput(String agentPhone, String id);

    }

    interface Presenter extends BasePresenter<View>{

        void getUserAttributes();

        void fetchNasabah();

        void postOTPRequestBorrowerAgent(String id);

    }
}
