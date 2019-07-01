package com.ayannah.bantenbank.screen.login;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loginComplete();

    }

    interface Presenter extends BasePresenter<View>{

        void getClientToken(String phone, String pass);

        void setUserIdentity();

    }
}
