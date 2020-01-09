package com.ayannah.asira.screen.borrower.tab_profile;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;

public interface ProfileContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int errorCode);

        void userIdentity(PreferenceRepository user);

        void logoutComplete();

    }

    interface Presenter extends BasePresenter<View>{

        void getUserIdentity();

        void doLogout();

    }
}
