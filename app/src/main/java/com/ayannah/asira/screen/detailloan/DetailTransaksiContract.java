package com.ayannah.asira.screen.detailloan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Loans.DataItem;

public interface DetailTransaksiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadAllInformation(DataItem dataItem);

        void showResultLoanOnProcess(boolean isExist);

    }

    interface Presenter extends BasePresenter<View>{

        void getInformationLoan(String idLoan);

        void checkLoanOnProcess();

        void getInformationLoanAgent(String id_loan);
    }
}
