package com.ayannah.asira.screen.agent.viewBorrower;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface ViewBorrowerContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter<View>{


    }
}
