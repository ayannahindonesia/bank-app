package com.ayannah.asira.screen.navigationmenu.infopribadi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class InfoPribadiActivity extends DaggerAppCompatActivity implements
        InfoPribadiContract.View,
        Validator.ValidationListener {

    @Inject
    InfoPribadiContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.jenisKelamin)
    TextView jenisKelamin;

    @BindView(R.id.etKTP)
    EditText etKTP;

    @BindView(R.id.etMomsName)
    EditText etMomsName;

    @NotEmpty(message = "Mohon diisi kolom ini")
    @BindView(R.id.etLamaMenempatiRumah)
    EditText etLamaMenempatiRumah;

    @NotEmpty(message = "Mohon isi nama pasangan", trim = true)
    @BindView(R.id.etSpouseName)
    EditText etSpouseName;

    @NotEmpty(message = "Mohon diisi kolom ini", trim = true)
    @BindView(R.id.etAddressBorrower)
    EditText etAddressBorrower;

    @BindView(R.id.rt)
    EditText rt;

    @BindView(R.id.rw)
    EditText rw;

    @BindView(R.id.etHomeNumber)
    EditText etHomeNumber;

    @BindView(R.id.etDateBirth)
    EditText etDateBirth;

    @BindView(R.id.etBirthPlace)
    EditText etBirthPlace;

    @Select(message = "Wajib diisi")
    @BindView(R.id.spCollageLevel)
    Spinner spCollageLevel;

    @Select(message = "Wajib diisi")
    @BindView(R.id.spPendidikan)
    Spinner spPendidikan;

    @Select(message = "Wajib diisi")
    @BindView(R.id.spPerkawinan)
    Spinner spPerkawinan;

    @Select(message = "Wajib diisi")
    @BindView(R.id.spProvinsi)
    Spinner spProvinsi;

    @Select(message = "Wajib diisi")
    @BindView(R.id.spTanggungan)
    Spinner spTanggungan;

    @Select(message = "Wajib diisi, pastikan sudah memilih Provinsi")
    @BindView(R.id.spKota)
    Spinner spKota;

    @Select(message = "Wajib diisi, pastikan sudah memilih Kota")
    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;

    @Select(message = "Wajib diisi, pastikan sudah memilih Kota")
    @BindView(R.id.spKelurahan)
    Spinner spKelurahan;

    @NotEmpty(message = "Mohon diisi tanggal lahir pasangan anda")
    @BindView(R.id.dateBirthSpouse)
    TextView dateBirthSpouse;

    @Select(message = "Wajib diisi")
    @BindView(R.id.spStatusHome)
    Spinner spStatusHome;

    @BindView(R.id.lySpouse)
    LinearLayout lySpouse;

    private String[] educationRepo = {"Pilih...", "S2", "S1", "SMA/SMK", "SMP", "Tidak ada status pendidikan"};
    private String[] statusPerkawinan = {"Pilih...", "Belum Menikah", "Menikah", "Duda", "Janda"};
    private String[] tanggungan = {"Pilih...", "0", "1", "2", "3", "4", "5", "Lebih dari 5"};
    private String[] statusTempatTinggal = {"Pilih...", "Milik sendiri", "Milik Keluarga", "Dinas", "Sewa"};
    List<String> names;
    String provinsi;
    String kota;
    String kecamatan;
    String kelurahan;

    DateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private Validator validator;
    private AlertDialog dialogAlert;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialogAlert = builder.create();

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

        validator = new Validator(this);
        validator.setValidationListener(this);

        names = new ArrayList<>();

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.item_custom_spinner, educationRepo);
        spCollageLevel.setAdapter(mAdapter);
        spPendidikan.setAdapter(mAdapter);

        ArrayAdapter<String> mAdapterPerkawinan = new ArrayAdapter<>(this, R.layout.item_custom_spinner, statusPerkawinan);
        spPerkawinan.setAdapter(mAdapterPerkawinan);

        ArrayAdapter<String> mAdapterTanggungan = new ArrayAdapter<>(this, R.layout.item_custom_spinner, tanggungan);
        spTanggungan.setAdapter(mAdapterTanggungan);

        ArrayAdapter<String> mAdapterStatusHome = new ArrayAdapter<>(this, R.layout.item_custom_spinner, statusTempatTinggal);
        spStatusHome.setAdapter(mAdapterStatusHome);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void showErrorMessage(String message) {
        dialogAlert.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadInfoPribadi(PreferenceRepository data) {

        etName.setText(data.getUserName());

        if (data.getUserGender().equals("M")) {
            jenisKelamin.setText("Laki-laki");
        } else {
            jenisKelamin.setText("Perempuan");
        }

        etKTP.setText(data.getIdCard());

        etMomsName.setText(data.getUserMotherName());

        etLamaMenempatiRumah.setText(data.getLivedFor());

        try {
            Date date = serverFormat.parse(data.getUserBirthdate());
            etDateBirth.setText(displayFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        etBirthPlace.setText(data.getUserBirthplace());

        //pendidikan
        for(int i=0; i < educationRepo.length; i++){

            if(educationRepo[i].equals(data.getUserLastEducation())){
                spCollageLevel.setSelection(i);
            }
        }

        //status pernikahan
        for(int i=0; i < statusPerkawinan.length; i++){

            if(statusPerkawinan[i].equals(data.getUserMarriageStatus())){
                spPerkawinan.setSelection(i);
            }
        }
        spPerkawinan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(statusPerkawinan[position].equals("Menikah") ){

                   lySpouse.setVisibility(View.VISIBLE);

                }else {

                    lySpouse.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //pendidikan spouse
        for(int i=0; i < educationRepo.length; i++){

            if(educationRepo[i].equals(data.getUserLastEducation())){
                spPendidikan.setSelection(i);
            }
        }

        //jumlah tanggunan
        for(int i=0; i < tanggungan.length; i++){

            if(tanggungan[i].equals(String.valueOf(data.getDependants()))){
                spTanggungan.setSelection(i);
            } else if (data.getDependants() >= 6) {
                spTanggungan.setSelection(7);
            }
        }

        mPresenter.getProvince();

        provinsi = data.getuserProvince();

        kota = data.getUserCity();

        kecamatan = data.getSubDistrict();

        kelurahan = data.getUrbanVillage();

        //status tempat tinggal
        for (int k = 0; k < statusTempatTinggal.length; k++) {
            if (data.getHomeOwnerShip().toLowerCase().equals(statusTempatTinggal[k].toLowerCase())) {
                spStatusHome.setSelection(k);
            }
        }

        etSpouseName.setText(data.getUserSpouseName());

        etAddressBorrower.setText(data.getUserAddress());

        rt.setText(data.getUserNeighbourAssociation());

        rw.setText(data.getUserHamlets());

        etHomeNumber.setText(data.getUserHomePhoneNumber());

        try {
            Date date = serverFormat.parse(data.getSpouserBirthdate());
            dateBirthSpouse.setText(displayFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.buttonConfirm)
    void onClickSubmitUpdate(){

        validator.validate();

    }


    @OnClick(R.id.dateBirthSpouse)
    void onClickDateBirthSpouse(){

        new SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .displayMinutes(false)
                .displayHours(false)
                .displayDays(false)
                .displayMonth(true)
                .displayYears(true)
                .displayDaysOfMonth(true)
                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {

                    }
                })
                .title("Tanggal lahir pasangan")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                        dateBirthSpouse.setText(sdf.format(date));

                    }
                }).display();
    }

    @Override
    public void successUpdateInfoPribadi() {
        Toast.makeText(this, "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
        dialogAlert.dismiss();

        Intent intent = new Intent(this, InfoPribadiActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProvices(List<Provinsi.Data> provinces) {
        List<String> idProvinces = new ArrayList<>();

        names.add("Pilih Provinsi...");
        idProvinces.add("0");
        for(Provinsi.Data data: provinces){
            names.add(data.getNama());
            idProvinces.add(data.getId());
        }

        ArrayAdapter<String> mAdapterProvince =  new ArrayAdapter<>(this, R.layout.item_custom_spinner, names);
        spProvinsi.setAdapter(mAdapterProvince);

        //check provinsi
        for(int i=0; i < names.size(); i++){

            if(names.get(i).equals(provinsi)){
                spProvinsi.setSelection(i);
            }
        }

        //show kecamatan
        spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    Toast.makeText(InfoPribadiActivity.this, names.get(position), Toast.LENGTH_SHORT).show();
                    mPresenter.getDistrict(idProvinces.get(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showDistrict(List<Kabupaten.KabupatenItem> districts) {

        List<String> names = new ArrayList<>();
        List<String> idKab = new ArrayList<>();

        names.add("Pilih Kebupaten...");
        idKab.add("0");
        for(Kabupaten.KabupatenItem data: districts){
            names.add(data.getNama());
            idKab.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.item_custom_spinner, names);
        spKota.setAdapter(mAdapter);

        //check kota
        for(int i=0; i < names.size(); i++){

            if(names.get(i).equals(kota)){
                spKota.setSelection(i);
            }
        }

        spKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    mPresenter.getSubDistrict(idKab.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void showSubDistrict(List<Kecamatan.KecatamanItem> subdistricts) {

        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        names.add("Pilih Kecamatan...");
        ids.add("0");
        for(Kecamatan.KecatamanItem data: subdistricts){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.item_custom_spinner, names);
        spKecamatan.setAdapter(mAdapter);

        //check kecamatan
        for(int i=0; i < names.size(); i++){

            if(names.get(i).equals(kecamatan)){
                spKecamatan.setSelection(i);
            }
        }

        spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    mPresenter.getKelurahan(ids.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void showKelurahan(List<Kelurahan.KelurahanItem> kelurahans) {

        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        names.add("Pilih Kelurahan...");
        ids.add("0");
        for(Kelurahan.KelurahanItem data: kelurahans){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.item_custom_spinner, names);
        spKelurahan.setAdapter(mAdapter);

        //check kelurahan
        for(int i=0; i < names.size(); i++){

            if(names.get(i).equals(kelurahan)){
                spKelurahan.setSelection(i);
            }
        }

        spKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    mPresenter.getKelurahan(ids.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onValidationSucceeded() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        setRequestUpdatePersonalInformation();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Yakin Akan Merubah Data?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();

    }

    private void setRequestUpdatePersonalInformation() {
        JsonObject json = new JsonObject();

        json.addProperty("last_education", spCollageLevel.getSelectedItem().toString());

        json.addProperty("marriage_status", spPerkawinan.getSelectedItem().toString());

        if(spPerkawinan.getSelectedItem().toString().equals("Menikah")){

            json.addProperty("spouse_name", etSpouseName.getText().toString());

            try {
                Date date = displayFormat.parse(dateBirthSpouse.getText().toString());
                json.addProperty("spouse_birthday", serverFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            json.addProperty("spouse_lasteducation", spPendidikan.getSelectedItem().toString());

        }else {

            json.addProperty("spouse_name", "");

            json.addProperty("spouse_birthday", "1900-01-01T00:00:00.000Z");

            json.addProperty("spouse_lasteducation", "");

        }

        json.addProperty("address", etAddressBorrower.getText().toString());

        json.addProperty("province", spProvinsi.getSelectedItem().toString());

        json.addProperty("city", spKota.getSelectedItem().toString());

        json.addProperty("subdistrict", spKecamatan.getSelectedItem().toString());

        json.addProperty("urban_village", spKelurahan.getSelectedItem().toString());

        json.addProperty("neighbour_association", rt.getText().toString());

        json.addProperty("hamlets", rw.getText().toString());

        json.addProperty("home_phonenumber", etHomeNumber.getText().toString());

        json.addProperty("lived_for", Integer.parseInt(etLamaMenempatiRumah.getText().toString()));

        json.addProperty("home_ownership", spStatusHome.getSelectedItem().toString());

        if (spTanggungan.getSelectedItem().toString().toLowerCase().equals("lebih dari 5")) {
            json.addProperty("dependants", 6);
        } else {
            json.addProperty("dependants", Integer.parseInt(spTanggungan.getSelectedItem().toString()));
        }


        dialogAlert.show();
        mPresenter.updateInfoPribadi(json);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for(ValidationError param: errors){

            View view = param.getView();

            String message = param.getCollatedErrorMessage(this);

            if(view instanceof EditText){

                ((EditText) view).setError(message);

                Toast.makeText(this, "Mohon lengkapi kolom yang belulm terisi", Toast.LENGTH_SHORT).show();
                
            }else if(view instanceof Spinner) {

                ((TextView) ((Spinner) view).getSelectedView()).setError(message);

                Toast.makeText(this, "Masih ada yang belum dipilih", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
