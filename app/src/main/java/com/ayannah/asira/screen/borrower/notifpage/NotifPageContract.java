package com.ayannah.asira.screen.borrower.notifpage;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

import java.util.List;

public interface NotifPageContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showDataNotif(List<String> results);

    }

    interface Presenter extends BasePresenter<View>{

        void getListNotification();

    }
}
