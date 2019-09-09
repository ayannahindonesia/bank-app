package com.ayannah.asira.screen.register.formBorrower;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;

import java.util.List;

public interface FormBorrowerContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message);

        void showProvices(List<Provinsi.Data> provinces);

        void showDistrict(List<Kabupaten.KabupatenItem> districts);

        void showSubDistrict(List<Kecamatan.KecatamanItem> subdistricts);

        void showKelurahan(List<Kelurahan.KelurahanItem> kelurahans);


    }

    interface Presenter extends BasePresenter<View>{

        void getProvince();

        void getDistrict(String idProvince);

        void getSubDistrict(String idDistrict);

        void getKelurahan(String idSubDistrict);

        void getUser();

        boolean checkLogin();
    }
}
