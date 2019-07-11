package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.google.gson.JsonObject;

public interface InfoPribadiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPribadi(PreferenceRepository data);

        void successUpdateInfoPribadi();

    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPribadiUser();

        void updateInfoPribadi(JsonObject json);
    }
}
