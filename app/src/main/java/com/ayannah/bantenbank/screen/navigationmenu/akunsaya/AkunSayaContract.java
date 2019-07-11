package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.local.PreferenceRepository;

public interface AkunSayaContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showDataUser(PreferenceRepository preferenceRepository);

        void berhasil();

    }

    interface Presenter extends BasePresenter<View>{

        void getDataUser();

        void updateDataUser(String email);

    }
}
