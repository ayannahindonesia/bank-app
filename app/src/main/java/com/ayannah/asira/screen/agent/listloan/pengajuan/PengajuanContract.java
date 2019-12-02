package com.ayannah.asira.screen.agent.listloan.pengajuan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;

public interface PengajuanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

    }

    interface Presenter extends BasePresenter<View>{


    }
}
