package com.ayannah.asira.screen.borrower.profile_menu.editinfopribadi;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.google.gson.JsonObject;

import java.util.List;

public interface EditInfoPribadiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPribadi(PreferenceRepository data);

        void showProvices(List<Provinsi.Data> provinces);

        void showDistrict(List<Kabupaten.KabupatenItem> districts);

        void showSubDistrict(List<Kecamatan.KecatamanItem> subdistricts);

        void showKelurahan(List<Kelurahan.KelurahanItem> kelurahans);

        void successUpfateInfoPribadi();

    }

    interface Presenter extends BasePresenter<View>{

        void getInfoPribadiUser();

        void getProvince();

        void getDistrict(String idProvince);

        void getSubDistrict(String idDistrict);

        void getKelurahan(String idSubDistrict);

        void updateInfoPribadi(JsonObject json);

    }
}
