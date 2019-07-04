package com.ayannah.bantenbank.screen.register.formBorrower;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.Kabupaten;
import com.ayannah.bantenbank.data.model.Kecamatan;
import com.ayannah.bantenbank.data.model.Kelurahan;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.ayannah.bantenbank.data.model.UserRegister;
import com.ayannah.bantenbank.screen.register.formjobearning.FormJobEarningActivity;
import com.ayannah.bantenbank.screen.register.formjobearning.FormJobEarningFragment;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FormBorrowerFragment extends BaseFragment implements FormBorrowerContract.View{

    private String gender = "";

    @Inject
    FormBorrowerContract.Presenter mPresenter;

    @BindView(R.id.regist_name)
    EditText etNameBorrower;

    @BindView(R.id.rgJenisKelamin)
    RadioGroup rgJenisKelamin;

    @BindView(R.id.regist_ktp)
    EditText etKTP;

    @BindView(R.id.regist_dateBirth)
    TextView tvDateBirthBorrower;

    @BindView(R.id.regist_dateBirthSpouse)
    TextView regist_dateBirthSpouse;

    @BindView(R.id.regist_tempatLahir)
    EditText etTempatLahir;

    @BindView(R.id.regist_namaIbu)
    EditText etNamaIbu;

    @BindView(R.id.regist_spouseName)
    EditText etNamaPasangan;

    @BindView(R.id.regist_alamatDomisili)
    EditText etAlamatDomisili;

    @BindView(R.id.regist_rt)
    EditText etRt;

    @BindView(R.id.regist_rw)
    EditText etRw;

    @BindView(R.id.regist_phoneBorrower)
    EditText etTelpRumah;

    @BindView(R.id.regist_lamaMenempatiRumah)
    EditText etLamaMenempatiRumah;

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

    @BindView(R.id.tvKota)
    TextView tvKota;

    @BindView(R.id.spKota)
    Spinner spKota;

    @BindView(R.id.tvKecamatan)
    TextView tvKecamatan;

    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;

    @BindView(R.id.tvKelurahan)
    TextView tvKelurahan;

    @BindView(R.id.lyRayon)
    LinearLayout lyRayon;

    @BindView(R.id.spKelurahan)
    Spinner spKelurahan;

    @BindView(R.id.spStatusHome)
    Spinner spStatusHome;

    private FormJobEarningFragment fragment = new FormJobEarningFragment();

    private DatePickerDialog datePickerDialog;

    SimpleDateFormat sdf;

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

        if (!mPresenter.checkLogin()) {
            mPresenter.getUser();
        }

    }


    @Override
    protected void initView(Bundle state) {
        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

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

    @OnClick(R.id.buttonNext)
    void onClickNext(){

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        bundle.putString(FormOtherFragment.REGIST_NAME, etNameBorrower.getText().toString());
        bundle.putString(FormOtherFragment.GENDER, gender);
        bundle.putString(FormOtherFragment.REGIST_BIRTHDATE, tvDateBirthBorrower.getText().toString());
        bundle.putString(FormOtherFragment.REGIST_BIRTHPLACE, etTempatLahir.getText().toString());
        bundle.putString(FormOtherFragment.REGIST_EDUCATION, spCollageLevel.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.MOTHER_NAME, etNamaIbu.getText().toString());
        bundle.putString(FormOtherFragment.MARITAL_STATUS, spPerkawinan.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.SPOUSE_NAME, etNamaPasangan.getText().toString());
        bundle.putString(FormOtherFragment.SPOUSE_BIRTHDATE, regist_dateBirthSpouse.getText().toString());
        bundle.putString(FormOtherFragment.SPOUSE_EDUCATION, spPendidikan.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.DEPENDANTS, spTanggungan.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.ADDRESS, etAlamatDomisili.getText().toString());
        bundle.putString(FormOtherFragment.PROVINCE, spProvinsi.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.CITY, spKota.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.SUB_DISTRICT, spKecamatan.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.DISTRICT, spKelurahan.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.REGIST_RT, etRt.getText().toString());
        bundle.putString(FormOtherFragment.REGIST_RW, etRw.getText().toString());
        bundle.putString(FormOtherFragment.REGIST_PHONE, etTelpRumah.getText().toString());
        bundle.putString(FormOtherFragment.HOME_STAY_YEAR, etLamaMenempatiRumah.getText().toString());
        bundle.putString(FormOtherFragment.HOME_STATUS, spStatusHome.getSelectedItem().toString());

//        UserRegister user = new UserRegister();

//        user.setEmployerName(etNameBorrower.getText().toString().trim());
//        user.setGender(etNameBorrower.getText().toString().trim());
//        user.setIdcardNumber(etNameBorrower.getText().toString().trim());
//        user.setBirthday(etNameBorrower.getText().toString().trim());
//        user.setLastEducation(etNameBorrower.getText().toString().trim());
//
//        user.setMotherName(etNameBorrower.getText().toString().trim());
//        user.setMarriageStatus(etNameBorrower.getText().toString().trim());
//        user.setSpouseName(etNameBorrower.getText().toString().trim());
//        user.setSpouseBirthday(etNameBorrower.getText().toString().trim());
//        user.setSpouseLasteducation(etNameBorrower.getText().toString().trim());
//        user.setDependants(etNameBorrower.getText().toString().trim()); //
//
//        user.setEmployerAddress(etNameBorrower.getText().toString().trim());
//        user.setProvince(etNameBorrower.getText().toString().trim());
//        user.setCity(etNameBorrower.getText().toString().trim());
//        user.setSubdistrict(etNameBorrower.getText().toString().trim()); //kecamatan
//        user.setUrbanVillage(etNameBorrower.getText().toString().trim()); //kelurahan
//        user.setNeighbourAssociation(etNameBorrower.getText().toString().trim()); //rt
//        user.setHamlets(etNameBorrower.getText().toString().trim()); //rw
//        user.setHomePhonenumber(etNameBorrower.getText().toString().trim());
//        user.setLivedFor(etNameBorrower.getText().toString().trim());
//        user.setHomeOwnership(etNameBorrower.getText().toString().trim());

        Intent formjob = new Intent(parentActivity(), FormJobEarningActivity.class);
        formjob.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        formjob.putExtras(bundle);
        startActivity(formjob);

    }

    @OnClick(R.id.regist_dateBirth)
    void onClickDateBirth(){

        new SingleDateAndTimePickerDialog.Builder(parentActivity())
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
                .title("Tanggal lahir")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

                        tvDateBirthBorrower.setText(sdf.format(date));
                    }
                }).display();

    }

    @OnClick(R.id.regist_dateBirthSpouse)
    void onClickDateBirthSpouse(){

        new SingleDateAndTimePickerDialog.Builder(parentActivity())


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
                .title("Tanggal lahir Pasangan")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

                        regist_dateBirthSpouse.setText(sdf.format(date));
                    }
                }).display();
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

        names.add("Pilih Kecamatan...");
        ids.add("0");
        for(Kelurahan.KelurahanItem data: kelurahans){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, names);
        spKelurahan.setAdapter(mAdapter);
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
    }
}
