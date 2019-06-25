package com.ayannah.bantenbank.screen.register.addaccountbank;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface AddAccountBankContract {

    interface View extends BaseView<Presenter>{


    }

    interface Presenter extends BasePresenter<View>{

        void loadAllBank();

    }
}
