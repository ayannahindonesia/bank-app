package com.ayannah.asira.screen.agent.loan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.data.model.ServiceProductsAgent;

import java.util.List;

public interface LoanAgentContract {

    interface View extends BaseView<Presenter> {

        void successGetProducts(ServiceProductsAgent resp);

        void showErrorMessage(String commonErrorFormat);

        void showReason(List<ReasonLoan.Data> data);

        void setupFormulaFee(String adminFeeSetup, String convfeeSetup);
    }

    interface Presenter extends BasePresenter<View> {


        void getProducts(String idService);

        void getLoanIntention();

        void getRulesFormula(String bankId);
    }
}
