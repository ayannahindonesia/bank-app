package com.ayannah.bantenbank.screen.register.adddoc;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface AddDocumentContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void successCheckMandotryEntity(String message);

    }

    interface Presenter extends BasePresenter<View>{

        void checkMandatoryItem(String ktp, String phoneNumber, String email);
    }
}
