package com.ayannah.bantenbank.screen.loan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.BankDetail;
import com.ayannah.bantenbank.data.model.ServiceProducts;

public interface LoanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void successGetProducts(ServiceProducts serviceProducts);
    }

    interface Presenter extends BasePresenter<View>{
        void getProducts();

    }
}
