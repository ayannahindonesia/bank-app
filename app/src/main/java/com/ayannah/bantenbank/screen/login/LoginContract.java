package com.ayannah.bantenbank.screen.login;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter<View>{

        void doLogin(String phone, String password);

        void testCredential();

    }
}
