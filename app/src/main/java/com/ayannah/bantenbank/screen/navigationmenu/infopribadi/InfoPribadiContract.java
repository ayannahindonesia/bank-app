package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.local.PreferenceRepository;

public interface InfoPribadiContract {

    interface View extends BaseView<Presenter>{

        void loadInfoPribadi(PreferenceRepository data);
    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPribadiUser();
    }
}
