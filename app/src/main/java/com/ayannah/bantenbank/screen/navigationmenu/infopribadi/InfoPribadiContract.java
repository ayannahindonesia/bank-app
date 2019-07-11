package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

import com.ayannah.bantenbank.base.BasePresenter;
import com.ayannah.bantenbank.base.BaseView;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.Kabupaten;
import com.ayannah.bantenbank.data.model.Kecamatan;
import com.ayannah.bantenbank.data.model.Kelurahan;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.google.gson.JsonObject;

import java.util.List;

public interface InfoPribadiContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void loadInfoPribadi(PreferenceRepository data);

        void successUpdateInfoPribadi();

        void showProvices(List<Provinsi.Data> provinces);

        void showDistrict(List<Kabupaten.KabupatenItem> districts);

        void showSubDistrict(List<Kecamatan.KecatamanItem> subdistricts);

        void showKelurahan(List<Kelurahan.KelurahanItem> kelurahans);

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
