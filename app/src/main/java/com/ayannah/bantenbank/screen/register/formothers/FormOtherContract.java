package com.ayannah.bantenbank.screen.register.formothers;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.ayannah.bantenbank.screen.login.LoginContract;
import com.google.gson.JsonObject;

public interface FormOtherContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void registerComplete();

        void successGetOTP();
    }

    interface Presenter extends BasePresenter<View> {

        void postRegisterBorrower(JsonObject jsonObject);

        void postBorrowerOTPRequest(String string);

    }
}
