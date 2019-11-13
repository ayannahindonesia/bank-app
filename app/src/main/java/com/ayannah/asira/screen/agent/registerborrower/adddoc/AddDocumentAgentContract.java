package com.ayannah.asira.screen.agent.registerborrower.adddoc;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface AddDocumentAgentContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void successCheckMandotryEntity(String message, String pnumber);

        void dialogDismiss();
    }

    interface Presenter extends BasePresenter<View> {

        void checkMandatoryItem(String ktp, String phoneNumber, String email,String npwp);

        void checkPublicToken();
    }
}
