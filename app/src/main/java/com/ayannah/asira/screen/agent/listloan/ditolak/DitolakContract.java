package com.ayannah.asira.screen.agent.listloan.ditolak;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface DitolakContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter<View> {


    }
}
