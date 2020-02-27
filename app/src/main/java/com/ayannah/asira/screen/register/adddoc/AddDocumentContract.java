package com.ayannah.asira.screen.register.adddoc;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.ayannah.asira.data.model.ReasonLoan;
import com.google.gson.JsonObject;

import java.util.List;

public interface AddDocumentContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void successCheckMandotryEntity(String message, String pnumber);

        void dialogDismiss();

        void showProvices(List<Provinsi.Data> semuaprovinsi);

        void showCity(List<Kabupaten.KabupatenItem> daftarKabupaten);

        void showKecamatan(List<Kecamatan.KecatamanItem> daftarKecamatan);

        void showKelurahan(List<Kelurahan.KelurahanItem> daftarDesa);

        void showCurrentProfile(PreferenceRepository preferenceRepository);

        void successUpdateProfile();
    }

    interface Presenter extends BasePresenter<View>{

        void checkMandatoryItem(String ktp, String phoneNumber, String email,String npwp);

        void getProvince();

        void getCity(String idProvince);

        void getKecamatan(String idCity);

        void getKelurahan(String idKecamatan);

        void getCurrentProfile();

        void patchUserProfile(JsonObject jsonObject);
    }
}
