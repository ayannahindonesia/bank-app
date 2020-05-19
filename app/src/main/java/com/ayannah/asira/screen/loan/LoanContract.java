package com.ayannah.asira.screen.loan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.FormDynamic;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;

import java.util.ArrayList;
import java.util.List;

public interface LoanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void successGetProducts(ServiceProducts serviceProducts);

        void showReason(List<ReasonLoan.Data> data);

    }

    interface Presenter extends BasePresenter<View>{

        void getProducts(String idService);

        void getReasonLoan();

        void setFormInfoToLocal(ArrayList<FormDynamic> arrFormForSend);
    }
}
