package com.ayannah.asira.screen.borrower.navigationmenu.akunsaya;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;

public interface AkunSayaContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showDataUser(PreferenceRepository preferenceRepository);

        void berhasil();

    }

    interface Presenter extends BasePresenter<View>{

        void getDataUser();

        void updateDataUser(String email, String nickname);

    }
}
