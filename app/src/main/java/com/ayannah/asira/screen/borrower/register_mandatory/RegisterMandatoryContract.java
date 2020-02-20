package com.ayannah.asira.screen.borrower.register_mandatory;

import android.text.Editable;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface RegisterMandatoryContract {

    interface View extends BaseView<Presenter> {

        void successCheckUnique();

        void successRequestOTP();

        void failedGetToken(String err);

        void successGetToken();

        void failedCheckUnique(String message);

        void loginComplete();

        void goToAgentLandingPage();
    }

    interface Presenter extends BasePresenter<View> {


        void checkUnqiue(String phone, String email);

        void requestOTP(String phone, int coba);

        void getToken();

        boolean isUserLogged();

        boolean isAgentLogged();
    }
}
