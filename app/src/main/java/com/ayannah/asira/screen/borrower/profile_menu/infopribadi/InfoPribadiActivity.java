package com.ayannah.asira.screen.borrower.profile_menu.infopribadi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.screen.borrower.profile_menu.editinfopribadi.EditInfoPribadiActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class InfoPribadiActivity extends DaggerAppCompatActivity implements
        InfoPribadiContract.View {

    @Inject
    InfoPribadiContract.Presenter mPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;

    //section first
    @BindView(R.id.etName) TextView etName;
    @BindView(R.id.jenisKelamin) TextView jenisKelamin;
    @BindView(R.id.etKTP) TextView etKTP;
    @BindView(R.id.etDateBirth) TextView dateBirth;
    @BindView(R.id.etBirthPlace) TextView birthPlace;
    @BindView(R.id.etCollageLevel) TextView collageLevel;
    @BindView(R.id.tvTanggungan) TextView jumlahTanggungan;
    @BindView(R.id.tvMomsName) TextView etMomsName;
    @BindView(R.id.tvPerkawinan) TextView statusPerkawinan;

    //sectuib status perkawinan jika menikah
    @BindView(R.id.lySpouse) LinearLayout lySpouse;

    //section data alamat pemohon
    @BindView(R.id.tvAlamat) TextView alamat;
    @BindView(R.id.tvProvinsi) TextView provinsi;
    @BindView(R.id.tvKota) TextView kota;
    @BindView(R.id.tvKecamatan) TextView kecamatan;
    @BindView(R.id.tvKelurahan) TextView kelurahan;
    @BindView(R.id.tvRt) TextView rt;
    @BindView(R.id.tvRw) TextView rw;
    @BindView(R.id.tvHomeNumber) TextView telpRumah;
    @BindView(R.id.tvLamaMenempati) TextView lamaMenempatiRumah;
    @BindView(R.id.tvHomeStatus) TextView statusKepemilikanRumah;
    @BindView(R.id.tvSpouseName) TextView namaPasangan;
    @BindView(R.id.tvSpouseBirthdate) TextView tglLahirPasangan;
    @BindView(R.id.tvSpouseEdu) TextView pendidikanPasangan;

    Locale locale = new Locale("in", "ID");
    private DateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", locale);
    private DateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",  locale);


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getInfoPribadiUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pribadi);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Informasi Pribadi");

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadInfoPribadi(PreferenceRepository data) {

        //section data pribadi
        dataPribadi(data);

        //section data alamat pemohon
        dataAlamatPemohon(data);


    }

    private void dataPribadi(PreferenceRepository data) {

        etName.setText(data.getUserName().toUpperCase());

        if (data.getUserGender().equals("M")) {
            jenisKelamin.setText("Laki-laki");
        } else {
            jenisKelamin.setText("Perempuan");
        }

        etKTP.setText(data.getIdCard());

        try {
            Date date = serverFormat.parse(data.getUserBirthdate());
            dateBirth.setText(displayFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        birthPlace.setText(data.getUserBirthplace());

        collageLevel.setText(data.getUserLastEducation());

        jumlahTanggungan.setText(String.format("%s orang", data.getDependants()));

        etMomsName.setText(data.getUserMotherName().toUpperCase());

        statusPerkawinan.setText(data.getUserMarriageStatus().toUpperCase());

        if (data.getUserMarriageStatus().toLowerCase().equals("menikah")) {
            try {
                Date date = serverFormat.parse(data.getSpouserBirthdate());

                lySpouse.setVisibility(View.VISIBLE);

                pendidikanPasangan.setText(data.getSpouseEducation());
                tglLahirPasangan.setText(displayFormat.format(date));
                namaPasangan.setText(data.getUserSpouseName());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private void dataAlamatPemohon(PreferenceRepository data) {

        alamat.setText(data.getUserAddress());

        provinsi.setText(data.getuserProvince().toUpperCase());

        kota.setText(data.getUserCity().toUpperCase());

        kecamatan.setText(data.getSubDistrict().toUpperCase());

        kelurahan.setText(data.getUrbanVillage().toUpperCase());

        rt.setText(data.getUserNeighbourAssociation());

        rw.setText(data.getUserHamlets());

        telpRumah.setText(data.getUserHomePhoneNumber());

        lamaMenempatiRumah.setText(String.format("%s Tahun", data.getLivedFor()));

        statusKepemilikanRumah.setText(data.getHomeOwnerShip().toUpperCase());

    }

    @OnClick(R.id.buttonConfirm)
    void onClickSubmitUpdate(){

        Intent intent = new Intent(this, EditInfoPribadiActivity.class);
        startActivity(intent);

    }

}
