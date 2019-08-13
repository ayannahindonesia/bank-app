package com.ayannah.bantenbank.screen.createnewpassword;

import android.widget.EditText;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

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
