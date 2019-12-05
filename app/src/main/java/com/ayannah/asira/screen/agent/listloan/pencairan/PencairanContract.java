package com.ayannah.asira.screen.agent.listloan.pencairan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.DummyLoanBorrower;

import java.util.List;

public interface PencairanContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void showOnPencairan(List<DummyLoanBorrower> results);

    }

    interface Presenter extends BasePresenter<View> {

        void getOnPencairan();

    }
}
