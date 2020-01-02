package com.ayannah.asira.screen.borrower.profile_menu.infopribadi;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;

public interface InfoPribadiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPribadi(PreferenceRepository data);

    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPribadiUser();

    }
}
