package com.ayannah.asira.screen.borrower.tab_rewards;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface RewardsContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int errorCode);

    }

    interface Presenter extends BasePresenter<View>{


    }
}
