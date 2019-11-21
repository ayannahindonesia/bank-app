package com.ayannah.asira.screen.agent.viewBorrower;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.model.UserProfile;

import java.util.List;

public interface ViewBorrowerContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String code);

        void getAllData(int totalData, List<UserProfile> results);

    }

    interface Presenter extends BasePresenter<View>{

        void getDataBorrower(String bankId);

    }
}
