package com.ayannah.asira.screen.agent.tab_pesan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface PesanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int code);

        void showAllNotification();


    }

    interface Presenter extends BasePresenter<View>{

        void getNotification();

    }
}
