package com.ayannah.asira.screen.agent.listloan.pencairan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.DummyLoanBorrower;
import com.ayannah.asira.data.model.Loans.DataItem;

import java.util.List;

public interface PencairanContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void showOnPencairan(List<DataItem> results);

    }

    interface Presenter extends BasePresenter<View> {

        void getOnPencairan(int idBank);

    }
}
