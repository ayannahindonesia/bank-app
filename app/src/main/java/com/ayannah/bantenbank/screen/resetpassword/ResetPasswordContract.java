package com.ayannah.bantenbank.screen.resetpassword;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface ResetPasswordContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showSuccessSendEmail(String message);
    }

    interface Presenter extends BasePresenter<View>{

        void clientAuth(String email);

        void sendEmail(String email);

    }
}
