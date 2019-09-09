package com.ayannah.asira.screen.register.addaccountbank;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface AddAccountBankContract {

    interface View extends BaseView<Presenter>{


    }

    interface Presenter extends BasePresenter<View>{

        void loadAllBank();

    }
}
