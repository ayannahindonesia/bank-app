package com.ayannah.asira.screen.borrower.navigationmenu.infokeuangan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
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
