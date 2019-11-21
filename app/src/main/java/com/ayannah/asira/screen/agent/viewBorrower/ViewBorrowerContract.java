package com.ayannah.asira.screen.agent.viewBorrower;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.NasabahAgent;

import java.util.List;

public interface ViewBorrowerContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void getAllData(List<NasabahAgent> results);

    }

    interface Presenter extends BasePresenter<View>{

        void getDataBorrower();

    }
}
