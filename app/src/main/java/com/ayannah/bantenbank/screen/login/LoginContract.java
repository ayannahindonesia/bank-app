package com.ayannah.bantenbank.screen.login;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void completeCreateUserToken();

        void isAccountWrong();

        void loginComplete();

    }

    interface Presenter extends BasePresenter<View>{

        //1. request public token to access all API for borrower
        void getPublicToken(String phone, String pass);

        //2. after get public token, we need to get token for borrower user and save it.
        // token borrower user will use to all borrower purposes in future
        void getClientToken(String phone, String pass);

        //3. set user for preferences
        void setUserIdentity();

        //4. usser is already logged or not
        boolean isUserLogged();


    }
}
