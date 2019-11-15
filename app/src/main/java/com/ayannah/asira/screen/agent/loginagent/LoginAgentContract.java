package com.ayannah.asira.screen.agent.loginagent;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface LoginAgentContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String errorMessage);

        void loginComplete();
    }

    interface Presenter extends BasePresenter<View> {

        void getPublicToken(String username, String password);
    }

}
