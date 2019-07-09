package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.local.PreferenceRepository;

public interface DataPendukungContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showDataPendukung(PreferenceRepository preferenceRepository);
    }

    interface Presenter extends BasePresenter<View>{

        void getDataPendukung();

    }
}
