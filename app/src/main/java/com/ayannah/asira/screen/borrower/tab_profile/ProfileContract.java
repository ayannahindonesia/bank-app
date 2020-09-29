package com.ayannah.asira.screen.borrower.tab_profile;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.google.gson.JsonObject;

public interface ProfileContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void userIdentity(PreferenceRepository user);

        void logoutComplete();

        void successSentRequest(String message);
    }

    interface Presenter extends BasePresenter<View>{

        void getUserIdentity();

        void doLogout();

        void requestDeleteAccount(JsonObject jsonObject);

        boolean isRequestDeleteShow();

        boolean isLoanStatusActive();
    }
}
