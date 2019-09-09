package com.ayannah.asira.screen.register.formothers;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.google.gson.JsonObject;

public interface FormOtherContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void registerComplete();

        void successGetOTP();

        void successGetUserToken();
    }

    interface Presenter extends BasePresenter<View> {

        void postRegisterBorrower(JsonObject jsonObject);

        void postBorrowerOTPRequest(String string);

        void getUserToken(String key, String pass, String regist);
    }
}
