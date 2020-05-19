package com.ayannah.asira.screen.summary;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.google.gson.JsonObject;

public interface SummaryTransactionContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessages(String message);

        void errorSendLoan(String message, int code);

        void successLoanApplication(String id_loan);

        void successGetOtp(String loanOTP, String id_loan);

        void cannotMakingLoan();
    }

    interface Presenter extends BasePresenter<View>{

        void loanApplication(JsonObject json);

        void requestOTPForLoan(String idLoan);

        void postLoanAgent(JsonObject json);

        void requestOTPForLoanAgent(String id_loan);

        void requestOTPPersonal(boolean isPersonal, int percobaan);

        String getFormInfoLocal();
    }
}
