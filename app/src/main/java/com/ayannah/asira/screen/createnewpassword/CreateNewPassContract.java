package com.ayannah.asira.screen.createnewpassword;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface CreateNewPassContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void successCreateNewPassword();
    }

    interface Presenter extends BasePresenter<View>{

        void confirmNewPassword(String password);

        void setUserToken(String lastPathSegment);

        void postResetPassword(String pass, String uuid);
    }
}
