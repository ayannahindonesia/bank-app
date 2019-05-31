package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;

public interface DataPendukungContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter<View>{


    }
}
