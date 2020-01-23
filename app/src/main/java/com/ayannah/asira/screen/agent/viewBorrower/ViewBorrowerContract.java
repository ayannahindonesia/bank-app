package com.ayannah.asira.screen.agent.viewBorrower;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.data.model.UserProfile;

import java.util.List;

public interface ViewBorrowerContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String code);

        void getAllData(int totalData, List<UserBorrower> results);

        void goToOTPInput(String agentPhone, String id);
    }

    interface Presenter extends BasePresenter<View>{

        void getDataBorrower(String bankId);

        void getLenderToken();

        void getTokenAdminLender();

        void setDataSelectedBorrower(UserBorrower user);

        void postOTPRequestBorrowerAgent(String id);
    }
}
