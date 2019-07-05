package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.model.UserProfile;

public interface InformasiKeuanganContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPekerjaanDanKeuangan(UserProfile user);
    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPekerjaanDanKeuangan();

    }
}
