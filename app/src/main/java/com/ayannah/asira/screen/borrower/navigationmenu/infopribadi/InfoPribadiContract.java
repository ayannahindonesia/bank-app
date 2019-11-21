package com.ayannah.asira.screen.borrower.navigationmenu.infopribadi;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.google.gson.JsonObject;

import java.util.List;

public interface InfoPribadiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPribadi(PreferenceRepository data);

    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPribadiUser();

    }
}
