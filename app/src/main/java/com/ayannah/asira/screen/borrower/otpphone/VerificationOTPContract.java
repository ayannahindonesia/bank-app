package com.ayannah.asira.screen.borrower.otpphone;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.google.gson.JsonObject;

public interface VerificationOTPContract {

    interface View extends BaseView<Presenter> {

        void OTPVerified();

        void successVerifyLoan();

        void completeCreateUserToken();

        void showErrorMessage(String connection_error);

        void loginComplete();
    }

    interface Presenter extends BasePresenter<View> {

        void postOTPVerify(JsonObject jsonObject);

        void postVerifyLoanByOTP(String idloan, JsonObject json);

        void resubmitLoanOTP(int idloan, String otpCode);

        void getPublicToken(String phone, String pass, String isFrom);

        void setUserIdentity();
    }
}
