package com.ayannah.asira.screen.agent.listloan.pengajuan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.DummyLoanBorrower;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.Loans.Loans;

import java.util.List;

public interface PengajuanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showOnProcessLoan(List<DataItem> results);

    }

    interface Presenter extends BasePresenter<View>{

        void getOnProcessLoan(int idbank);

    }
}
