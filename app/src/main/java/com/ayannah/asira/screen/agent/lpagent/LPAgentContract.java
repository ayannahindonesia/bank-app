package com.ayannah.asira.screen.agent.lpagent;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface LPAgentContract {

    interface View extends BaseView<Presenter>{


        void displayUserIdentity(String agentName, String agentUserName, String agentID, String agentProvider);

        void successsLogout();
    }

    interface Presenter extends BasePresenter<View>{


        void getCurrentAgentIdentity();

        void logout();
    }
}
