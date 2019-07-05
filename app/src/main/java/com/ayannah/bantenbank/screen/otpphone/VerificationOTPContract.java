package com.ayannah.bantenbank.screen.otpphone;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.google.gson.JsonObject;

public interface VerificationOTPContract {

    interface View extends BaseView<Presenter> {

        void OTPVerified();
    }

    interface Presenter extends BasePresenter<View> {

        void postOTPVerify(JsonObject jsonObject);
    }
}
