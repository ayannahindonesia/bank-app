package com.ayannah.asira.screen.agent.lpagent;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface LPAgentContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String errorResponseWithStatusCode);
    }

    interface Presenter extends BasePresenter<View>{


        void getTokenLender();
    }
}
