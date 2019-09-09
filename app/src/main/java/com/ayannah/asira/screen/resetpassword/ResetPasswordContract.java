package com.ayannah.asira.screen.resetpassword;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

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
