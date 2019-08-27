package com.ayannah.bantenbank.screen.detailloan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.google.gson.JsonObject;

public interface DetailTransaksiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadAllInformation(DataItem dataItem);

        void showResultLoanOnProcess(boolean isExist);

    }

    interface Presenter extends BasePresenter<View>{

        void getInformationLoan(String idLoan);

        void checkLoanOnProcess();

    }
}
