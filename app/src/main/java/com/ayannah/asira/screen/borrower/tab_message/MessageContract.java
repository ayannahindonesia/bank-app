package com.ayannah.asira.screen.borrower.tab_message;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Notif;

import java.util.List;

public interface MessageContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int errorCode);

        void showDataNotif(List<Notif.Data> data);
    }

    interface Presenter extends BasePresenter<View>{


        void getNotification();
    }
}
