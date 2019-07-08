package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.local.PreferenceRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class InfoPribadiActivity extends DaggerAppCompatActivity implements InfoPribadiContract.View {

    @Inject
    InfoPribadiContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etName)
    EditText ip_name;

    @BindView(R.id.rbMale)
    RadioButton rbMale;

    @BindView(R.id.rbFemale)
    RadioButton rbFemale;

    @BindView(R.id.etKTP)
    EditText etKTP;

    @BindView(R.id.etMomsName)
    EditText etMomsName;

    @BindView(R.id.etLamaMenempatiRumah)
    EditText etLamaMenempatiRumah;

    @BindView(R.id.etSpouseName)
    EditText etSpouseName;

    @BindView(R.id.etAddressBorrower)
    EditText etAddressBorrower;

    @BindView(R.id.rt)
    EditText rt;

    @BindView(R.id.rw)
    EditText rw;

    @BindView(R.id.etHomeNumber)
    EditText etHomeNumber;

    @BindView(R.id.spCollageLevel)
    Spinner spCollageLevel;

    @BindView(R.id.spPendidikan)
    Spinner spPendidikan;

    @BindView(R.id.spPerkawinan)
    Spinner spPerkawinan;

    @BindView(R.id.spProvinsi)
    Spinner spProvinsi;

    @BindView(R.id.spTanggungan)
    Spinner spTanggungan;

    @BindView(R.id.spKota)
    Spinner spKota;

    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;

    @BindView(R.id.spKelurahan)
    Spinner spKelurahan;

    @BindView(R.id.spStatusHome)
    Spinner spStatusHome;

    private String[] educationRepo = {"S2", "S1", "SMA/SMK", "SMP", "Tidak ada status pendidikan"};
    private String[] statusPerkawinan = {"Belum Menikah", "Menikah", "Duda", "Janda"};
    private String[] tanggungan = {"0", "1", "2", "3", "4", "5", "Lebih dari 5"};
    private String[] statusTempatTinggal = {"Milik sendiri", "Milik Keluarga", "Dinas", "Sewa"};

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

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.item_custom_spinner, educationRepo);
        spCollageLevel.setAdapter(mAdapter);
        spPendidikan.setAdapter(mAdapter);

        ArrayAdapter<String> mAdapterPerkawinan = new ArrayAdapter<>(this, R.layout.item_custom_spinner, statusPerkawinan);
        spPerkawinan.setAdapter(mAdapterPerkawinan);

        ArrayAdapter<String> mAdapterTanggungan = new ArrayAdapter<>(this, R.layout.item_custom_spinner, tanggungan);
        spTanggungan.setAdapter(mAdapterTanggungan);

        ArrayAdapter<String> mAdapterStatusHome = new ArrayAdapter<>(this, R.layout.item_custom_spinner, statusTempatTinggal);
        spStatusHome.setAdapter(mAdapterStatusHome);

        loadProvinsi();
        loadKota();
        loadKec();
        loadKel();
    }

    private void loadProvinsi() {

        InputStream is = getResources().openRawResource(R.raw.provinsi);
        String jsonProvinsi = null;

        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonProvinsi = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> provinsiRepo = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonProvinsi);
            JSONArray arry = obj.getJSONArray("semuaprovinsi");

            for(int i = 0; i< arry.length(); i++){

               provinsiRepo.add(arry.getJSONObject(i).getString("nama"));

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> mAdapterProvince =  new ArrayAdapter<>(this, R.layout.item_custom_spinner, provinsiRepo);
        spProvinsi.setAdapter(mAdapterProvince);

    }

    private void loadKota() {

        InputStream is = getResources().openRawResource(R.raw.kab_banten);
        String jsonProvinsi = null;

        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonProvinsi = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> provinsiRepo = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonProvinsi);
            JSONArray arry = obj.getJSONArray("kabupatens");

            for(int i = 0; i< arry.length(); i++){

                provinsiRepo.add(arry.getJSONObject(i).getString("nama"));

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> mAdapterKota =  new ArrayAdapter<>(this, R.layout.item_custom_spinner, provinsiRepo);
        spKota.setAdapter(mAdapterKota);

    }

    private void loadKec() {

        InputStream is = getResources().openRawResource(R.raw.kec_banten);
        String jsonProvinsi = null;

        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonProvinsi = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> provinsiRepo = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonProvinsi);
            JSONArray arry = obj.getJSONArray("kecamatans");

            for(int i = 0; i< arry.length(); i++){

                provinsiRepo.add(arry.getJSONObject(i).getString("nama"));

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> mAdapterKec =  new ArrayAdapter<>(this, R.layout.item_custom_spinner, provinsiRepo);
        spKecamatan.setAdapter(mAdapterKec);

    }

    private void loadKel(){
        InputStream is = getResources().openRawResource(R.raw.kel_banten);
        String jsonProvinsi = null;

        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonProvinsi = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> provinsiRepo = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonProvinsi);
            JSONArray arry = obj.getJSONArray("desas");

            for(int i = 0; i< arry.length(); i++){

                provinsiRepo.add(arry.getJSONObject(i).getString("nama"));

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> mAdapterProvince =  new ArrayAdapter<>(this, R.layout.item_custom_spinner, provinsiRepo);
        spKelurahan.setAdapter(mAdapterProvince);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void loadInfoPribadi(PreferenceRepository data) {

        ip_name.setText(data.getUserName());

        if(data.getUserGender().equals("M")){
            rbMale.setChecked(true);
        }else {
            rbFemale.setChecked(true);
        }

        etKTP.setText(data.getIdCard());

        etMomsName.setText(data.getUserMotherName());

        etLamaMenempatiRumah.setText(data.getLivedFor());

        etSpouseName.setText(data.getUserSpouseName());

        etAddressBorrower.setText(data.getUserAddress());

        rt.setText(data.getUserNeighbourAssociation());

        rw.setText(data.getUserHamlets());

        etHomeNumber.setText(data.getUserHomePhoneNumber());

    }
}
