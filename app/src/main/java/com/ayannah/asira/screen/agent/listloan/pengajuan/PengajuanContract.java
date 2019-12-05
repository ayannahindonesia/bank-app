package com.ayannah.asira.screen.agent.listloan.pengajuan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.DummyLoanBorrower;

import java.util.List;

public interface PengajuanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showOnProcessLoan(List<DummyLoanBorrower> results);

    }

    interface Presenter extends BasePresenter<View>{

        void getOnProcessLoan();

    }
}
