package com.ayannah.asira.screen.borrower.profile_menu.datapendukung;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.google.gson.JsonObject;

public interface DataPendukungContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showDataPendukung(PreferenceRepository preferenceRepository);

        void successUpdateOtherData();
    }

    interface Presenter extends BasePresenter<View>{

        void getDataPendukung();

        void patchOtherData(JsonObject jsonObject);
    }
}
