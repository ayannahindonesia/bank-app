package com.ayannah.asira.screen.register.formBorrower;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.ayannah.asira.screen.register.formjobearning.FormJobEarningActivity;
import com.ayannah.asira.screen.register.formjobearning.FormJobEarningFragment;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.util.DatePickerCustom;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
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
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class FormBorrowerFragment extends BaseFragment implements FormBorrowerContract.View, Validator.ValidationListener {

    private String gender = "";

    @Inject
    FormBorrowerContract.Presenter mPresenter;

    @NotEmpty(message = "Masukan Nama Anda", trim = true)
    @BindView(R.id.regist_name)
    EditText etNameBorrower;

    @Checked(message = "Pilih Jenis Kelamin Anda")
    @BindView(R.id.rgJenisKelamin)
    RadioGroup rgJenisKelamin;

    @BindView(R.id.regist_dateBirth)
    TextView tvDateBirthBorrower;

    @BindView(R.id.regist_dateBirthSpouse)
    TextView regist_dateBirthSpouse;

    @BindView(R.id.tvGender)
    TextView tvGender;

    @NotEmpty(message = "Masukan Tempat Lahir Anda", trim = true)
    @BindView(R.id.regist_tempatLahir)
    EditText etTempatLahir;

    @NotEmpty(message = "Masukan Nama Ibu", trim = true)
    @BindView(R.id.regist_namaIbu)
    EditText etNamaIbu;

    @BindView(R.id.regist_spouseName)
    @NotEmpty(trim = true)
    EditText etNamaPasangan;

    @NotEmpty(message = "Masukan Alamat Anda", trim = true)
    @BindView(R.id.regist_alamatDomisili)
    EditText etAlamatDomisili;

    @NotEmpty(message = "Masukan RT", trim = true)
    @BindView(R.id.regist_rt)
    EditText etRt;

    @NotEmpty(message = "Masukan RW", trim = true)
    @BindView(R.id.regist_rw)
    EditText etRw;

    @BindView(R.id.regist_phoneBorrower)
    EditText etTelpRumah;

    @NotEmpty(message = "Masukan Lama Menempati Rumah", trim = true)
    @BindView(R.id.regist_lamaMenempatiRumah)
    EditText etLamaMenempatiRumah;

    @BindView(R.id.spCollageLevel)
    Spinner spCollageLevel;

    @BindView(R.id.spPendidikan)
    Spinner spPendidikan;

    @BindView(R.id.spPerkawinan)
    Spinner spPerkawinan;

    @Select
    @BindView(R.id.spProvinsi)
    Spinner spProvinsi;

    @BindView(R.id.spTanggungan)
    Spinner spTanggungan;

    @BindView(R.id.tvKota)
    TextView tvKota;

    @Select
    @BindView(R.id.spKota)
    Spinner spKota;

    @BindView(R.id.tvKecamatan)
    TextView tvKecamatan;

    @Select
    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;

    @BindView(R.id.tvKelurahan)
    TextView tvKelurahan;

    @BindView(R.id.lyRayon)
    LinearLayout lyRayon;

    @Select
    @BindView(R.id.spKelurahan)
    Spinner spKelurahan;

    @BindView(R.id.spStatusHome)
    Spinner spStatusHome;

    @BindView(R.id.rgNationality)
    RadioGroup rgNationality;

    @BindView(R.id.rbWNA)
    RadioButton rbwna;

    @NotEmpty(trim = true, message = "Masukan Nama Panggilan Anda")
    @BindView(R.id.regist_nickname)
    EditText regist_nickname;

    private Validator validator;

    private FormJobEarningFragment fragment = new FormJobEarningFragment();

    private DatePickerDialog datePickerDialog;

    DateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("in", "ID"));
    DateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private String[] educationRepo = {"S2", "S1", "SMA/SMK", "SMP", "Tidak ada status pendidikan"};
    private String[] statusPerkawinan = {"Belum Menikah", "Menikah", "Duda", "Janda"};
    private String[] tanggungan = {"0", "1", "2", "3", "4", "5", "Lebih dari 5"};
    private String[] statusTempatTinggal = {"Milik sendiri", "Milik Keluarga", "Dinas", "Sewa"};

    @Inject
    public FormBorrowerFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_borrower_identity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getProvince();

        mPresenter.getUser();

    }


    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, educationRepo);
        spCollageLevel.setAdapter(mAdapter);
        spPendidikan.setAdapter(mAdapter);

        ArrayAdapter<String> mAdapterPerkawinan = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, statusPerkawinan);
        spPerkawinan.setAdapter(mAdapterPerkawinan);

        ArrayAdapter<String> mAdapterTanggungan = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, tanggungan);
        spTanggungan.setAdapter(mAdapterTanggungan);

        ArrayAdapter<String> mAdapterStatusHome = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, statusTempatTinggal);
        spStatusHome.setAdapter(mAdapterStatusHome);


    }

    @OnItemSelected(R.id.spPerkawinan)
    void onClickStatus() {

        if (spPerkawinan.getSelectedItem().toString().equals("Menikah")) {

            etNamaPasangan.setEnabled(true);

            regist_dateBirthSpouse.setEnabled(true);

            spPendidikan.setEnabled(true);

        } else {

            etNamaPasangan.setEnabled(false);
            etNamaPasangan.setText("");

            regist_dateBirthSpouse.setEnabled(false);
            regist_dateBirthSpouse.setText(R.string.birthdate_format);

            spPendidikan.setEnabled(false);

        }
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){

        etNamaPasangan.setError(null);

        regist_dateBirthSpouse.setError(null);

        if (tvDateBirthBorrower.getText().equals("dd-mm-yyyy")) {

            tvDateBirthBorrower.setError("Pilih Tanggal Lahir Anda");

            tvDateBirthBorrower.setFocusableInTouchMode(true);

            tvDateBirthBorrower.requestFocus();

            Toast.makeText(parentActivity(), "Pilih Tanggal Lahir Anda", Toast.LENGTH_LONG).show();

        } else if (spPerkawinan.getSelectedItem().toString().equals("Menikah")) {

            if (etNamaPasangan.getText().toString().equals("")) {

                etNamaPasangan.setError("Masukan Nama Pasangan Anda");

                etNamaPasangan.requestFocus();

            } else if (regist_dateBirthSpouse.getText().toString().equals("dd-mm-yyyy")) {

                regist_dateBirthSpouse.setError("");

                Toast.makeText(parentActivity(), "Pilih Tanggal Lahir Pasangan Anda", Toast.LENGTH_LONG).show();

            } else {

                validator.validate();
            }

        } else {

            validator.validate();
        }

    }

    @OnClick(R.id.regist_dateBirth)
    void onClickDateBirth(){

        DialogFragment datePicker = new DatePickerCustom();
        datePicker.showNow(parentActivity().getSupportFragmentManager(), "DatePicker datebirth user");
        ((DatePickerCustom) datePicker).setOnSelectedDate(new DatePickerCustom.DatePickerCustomListener() {
            @Override
            public void onSelectedDate(String selectedDate) {
                tvDateBirthBorrower.setText(selectedDate);
                tvDateBirthBorrower.setError(null);
            }
        });

    }

    @OnClick(R.id.regist_dateBirthSpouse)
    void onClickDateBirthSpouse(){

        DialogFragment datePicker = new DatePickerCustom();
        datePicker.showNow(parentActivity().getSupportFragmentManager(), "DatePicker datebirth user");
        ((DatePickerCustom) datePicker).setOnSelectedDate(new DatePickerCustom.DatePickerCustomListener() {
            @Override
            public void onSelectedDate(String selectedDate) {
                regist_dateBirthSpouse.setText(selectedDate);
            }
        });
    }


    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProvices(List<Provinsi.Data> provinces) {

        List<String> names = new ArrayList<>();
        List<String> idProvinces = new ArrayList<>();

        names.add("Pilih Provinsi...");
        idProvinces.add("0");
        for(Provinsi.Data data: provinces){
            names.add(data.getNama());
            idProvinces.add(data.getId());
        }

        ArrayAdapter<String> mAdapterProvince =  new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, names);
        spProvinsi.setAdapter(mAdapterProvince);

        //show kecamatan
        spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    Toast.makeText(parentActivity(), names.get(position), Toast.LENGTH_SHORT).show();
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

        tvKota.setVisibility(View.VISIBLE);
        spKota.setVisibility(View.VISIBLE);

        List<String> names = new ArrayList<>();
        List<String> idKab = new ArrayList<>();

        names.add("Pilih Kebupaten...");
        idKab.add("0");
        for(Kabupaten.KabupatenItem data: districts){
            names.add(data.getNama());
            idKab.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, names);
        spKota.setAdapter(mAdapter);
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

        tvKecamatan.setVisibility(View.VISIBLE);
        spKecamatan.setVisibility(View.VISIBLE);

        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        names.add("Pilih Kecamatan...");
        ids.add("0");
        for(Kecamatan.KecatamanItem data: subdistricts){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, names);
        spKecamatan.setAdapter(mAdapter);
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

        tvKelurahan.setVisibility(View.VISIBLE);
        spKelurahan.setVisibility(View.VISIBLE);
        lyRayon.setVisibility(View.VISIBLE);

        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        names.add("Pilih Kelurahan...");
        ids.add("0");
        for(Kelurahan.KelurahanItem data: kelurahans){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, names);
        spKelurahan.setAdapter(mAdapter);
//        spKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if(position != 0){
//                    mPresenter.getKelurahan(ids.get(position));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    @OnClick({R.id.rbPria, R.id.rbPerempuan})
    public void onRadioButtonClicked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();

        switch (radioButton.getId()) {
            case R.id.rbPria:
                if (checked) {
                    gender = "M";
                }
                break;
            case R.id.rbPerempuan:
                if (checked) {
                    gender = "F";
                }
                break;
        }
        tvGender.setError(null);
    }

    @Override
    public void onValidationSucceeded() {

        try {
            Bundle bundle = parentActivity().getIntent().getExtras();
            assert bundle != null;

            Date dateBorrower = displayFormat.parse(tvDateBirthBorrower.getText().toString());

            bundle.putString(FormOtherFragment.REGIST_NAME, etNameBorrower.getText().toString());
            bundle.putString(FormOtherFragment.GENDER, gender);
            bundle.putString(FormOtherFragment.REGIST_BIRTHDATE, serverFormat.format(dateBorrower));
            bundle.putString(FormOtherFragment.REGIST_BIRTHPLACE, etTempatLahir.getText().toString());
            bundle.putString(FormOtherFragment.REGIST_EDUCATION, spCollageLevel.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.MOTHER_NAME, etNamaIbu.getText().toString());
            bundle.putString(FormOtherFragment.MARITAL_STATUS, spPerkawinan.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.SPOUSE_NAME, etNamaPasangan.getText().toString());
            if (regist_dateBirthSpouse.getText().toString().equals("dd-mm-yyyy")) {
                bundle.putString(FormOtherFragment.SPOUSE_BIRTHDATE, null);
            } else {
                Date dateSpouse = displayFormat.parse(regist_dateBirthSpouse.getText().toString());
                bundle.putString(FormOtherFragment.SPOUSE_BIRTHDATE, String.valueOf(serverFormat.format(dateSpouse)));
            }
            if (spPerkawinan.getSelectedItem().toString().toLowerCase().equals("menikah")) {
                bundle.putString(FormOtherFragment.SPOUSE_EDUCATION, spPendidikan.getSelectedItem().toString());
            } else {
                bundle.putString(FormOtherFragment.SPOUSE_EDUCATION, null);
            }
            if (spTanggungan.getSelectedItem().toString().toLowerCase().equals("lebih dari 5")) {
                bundle.putString(FormOtherFragment.DEPENDANTS, "6");
            } else {
                bundle.putString(FormOtherFragment.DEPENDANTS, spTanggungan.getSelectedItem().toString());
            }


            bundle.putString(FormOtherFragment.ADDRESS, etAlamatDomisili.getText().toString());
            bundle.putString(FormOtherFragment.PROVINCE, spProvinsi.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.CITY, spKota.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.SUB_DISTRICT, spKecamatan.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.DISTRICT, spKelurahan.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.REGIST_RT, etRt.getText().toString());
            bundle.putString(FormOtherFragment.REGIST_RW, etRw.getText().toString());
            bundle.putString(FormOtherFragment.REGIST_PHONE, etTelpRumah.getText().toString().trim());
            bundle.putString(FormOtherFragment.HOME_STAY_YEAR, etLamaMenempatiRumah.getText().toString());
            bundle.putString(FormOtherFragment.HOME_STATUS, spStatusHome.getSelectedItem().toString());
            bundle.putString(FormOtherFragment.NICKNAME, regist_nickname.getText().toString());

            Intent formjob = new Intent(parentActivity(), FormJobEarningActivity.class);
            formjob.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            formjob.putExtras(bundle);
            startActivity(formjob);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                view.requestFocus();
            } else if (view instanceof Spinner) {
                ((TextView) ((Spinner) view).getSelectedView()).setError(message);
                view.requestFocus();
            } else if (view instanceof TextView) {
                ((TextView) view).setError(message);
                view.requestFocus();
            } else if (view instanceof RadioGroup) {
                ((RadioGroup) view).requestFocus();
                ((RadioGroup) view).requestFocusFromTouch();
                tvGender.setError(message);
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
