package com.ayannah.asira.screen.agent.listloan.pencairan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface PencairanContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter<View> {


    }
}
