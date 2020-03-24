package com.ayannah.asira.screen.agent.registerborrower.formother;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.google.gson.JsonObject;

public interface FormOtherAgentContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void registerComplete(String id_borrower, String phone_agent);

        void otpIssuedREgisterComplete(String message, String id_borrower); //for condition error code 422
    }

    interface Presenter extends BasePresenter<View> {

        void postRegisterBorrower(JsonObject jsonObject);

    }
}
