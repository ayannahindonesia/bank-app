package com.ayannah.bantenbank.screen.summary;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.google.gson.JsonObject;

public interface SummaryTransactionContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessages(String message);

        void successLoanApplication(String id_loan);

        void successGetOtp(String loanOTP, String id_loan);

    }

    interface Presenter extends BasePresenter<View>{

        void loanApplication(JsonObject json);

        void requestOTPForLoan(String idLoan);

    }
}
