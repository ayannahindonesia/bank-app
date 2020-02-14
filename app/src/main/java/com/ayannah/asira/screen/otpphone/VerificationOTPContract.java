package com.ayannah.asira.screen.otpphone;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.google.gson.JsonObject;

public interface VerificationOTPContract {

    interface View extends BaseView<Presenter> {

        void OTPVerified();

        void successVerifyLoan();

        void completeCreateUserToken();

        void showErrorMessage(String message, int codeError);

        void loginComplete();

        void successCreateBorrower();

        void errorFCM(String errorMessage);

        void successRequestOTP();

        void callAPIGetClientToken();
    }

    interface Presenter extends BasePresenter<View> {

        void postOTPVerify(JsonObject jsonObject);

        void postVerifyLoanByOTP(String idloan, JsonObject json);

        void resubmitLoanOTP(int idloan, String otpCode);

        void getPublicToken(String phone, String pass, String isFrom);

        void setUserIdentity();

        void postVerifyLoanByOTPAgent(String id_loan, JsonObject json);

        void postOTPforRegisterBorrower(String id_borrower, String otp_code);

        void resendOTP(String personalPhone);

        void postBorrowerRegister(JsonObject jsonObject);

        void getClientToken(String phone, String pass, String isFrom);
    }
}
