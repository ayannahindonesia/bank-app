package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.google.gson.JsonObject;

public interface InformasiKeuanganContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPekerjaanDanKeuangan(PreferenceRepository preferenceRepository);

        void successUpdateJobEarningData();
    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPekerjaanDanKeuangan();

        void patchJobEarningData(JsonObject jsonObject);
    }
}
