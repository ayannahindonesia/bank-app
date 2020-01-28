package com.ayannah.asira.screen.borrower.login;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.google.gson.JsonObject;

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void completeCreateUserToken();

        void isAccountWrong();

        void loginComplete();

        void accountNotOTP();

        void errorFCM(String message);

        void successGetOTP();
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


        void postRequestOTP(JsonObject jsonObject);
    }
}
