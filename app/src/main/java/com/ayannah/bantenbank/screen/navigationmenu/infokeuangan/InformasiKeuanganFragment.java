package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.ayannah.bantenbank.util.NumberSeparatorTextWatcher;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InformasiKeuanganFragment extends BaseFragment implements InformasiKeuanganContract.View, Validator.ValidationListener {

    @Inject
    InformasiKeuanganContract.Presenter mPresenter;

    @BindView(R.id.spJenisPekerjaan)
    Spinner spJenisPekerjaan;

    @NotEmpty(message = "Masukan Nomor Induk Pegawai Anda")
    @BindView(R.id.noPegawai)
    EditText noIndukPegawai;

    @NotEmpty(message = "Masukan Nama Perusahaan\nTempat Anda Bekerja")
    @BindView(R.id.namaInstansi)
    EditText namaInstansi;

    @NotEmpty(message = "Masukan Lama Anda Bekerja")
    @BindView(R.id.lamaBekerja)
    EditText lamaBekerja;

    @NotEmpty(message = "Masukan Alamat Perusahaan\nTempat Anda Bekerja")
    @BindView(R.id.etAlamatKantor)
    EditText etAlamatKantor;

    @NotEmpty(message = "Masukan No Telpon Perusahaan\nTempat Anda Bekerja")
    @BindView(R.id.etPhone)
    EditText etPhone;

    @NotEmpty(message = "Masukan Nama Atasan Anda")
    @BindView(R.id.namaAtasan)
    EditText namaAtasan;

    @NotEmpty(message = "Masukan Jabatan Anda")
    @BindView(R.id.jabatan)
    EditText jabatan;


    @NotEmpty(message = "Masukan Gaji Anda")
    @BindView(R.id.etPenghasilan)
    EditText etPenghasilan;

    @BindView(R.id.etPendapatanLain)
    EditText etPendapatanLain;

    @BindView(R.id.etSumberPendapatanLain)
    EditText etSumberPendapatanLain;

    private String[] jobRepo = {"Pemerintahan", "CPNS", "Pegawai Swasta", "Kepala Daerah", "Pegawai Pemerintah Nasional", "Pegawai Pemerintah Daerah"};
    private Validator validator;

    @Inject
    public InformasiKeuanganFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_info_keuangan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getInfoPekerjaanDanKeuangan();

    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, jobRepo);
        spJenisPekerjaan.setAdapter(mAdapter);

        NumberSeparatorTextWatcher penghasilanNumberSeparator = new NumberSeparatorTextWatcher(etPenghasilan);
        etPenghasilan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etPenghasilan.addTextChangedListener(penghasilanNumberSeparator);
                }
//                else {
//                    etPenghasilan.removeTextChangedListener(penghasilanNumberSeparator);
//                }
            }
        });

        NumberSeparatorTextWatcher pendapatanLainNumberSeparator = new NumberSeparatorTextWatcher(etPendapatanLain);
        etPendapatanLain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etPendapatanLain.addTextChangedListener(pendapatanLainNumberSeparator);
                }
//                else {
//                    etPendapatanLain.removeTextChangedListener(pendapatanLainNumberSeparator);
//                }
            }
        });

    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        validator.validate();

//        Toast.makeText(parentActivity(), "submit", Toast.LENGTH_SHORT).show();

    }

    private void updateDataProfile() {
        int otherincome;
        String pendapatan_lain = etPendapatanLain.getText().toString().replaceAll("[.,]", "");
        String primary_income = etPenghasilan.getText().toString().replaceAll("[.,]", "");

        if (!etPendapatanLain.getText().toString().equals("")) {
            otherincome = Integer.parseInt(pendapatan_lain);
        } else {
            otherincome = 0;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("field_of_work", spJenisPekerjaan.getSelectedItem().toString());
        jsonObject.addProperty("department", spJenisPekerjaan.getSelectedItem().toString());
        jsonObject.addProperty("employee_id", noIndukPegawai.getText().toString());
        jsonObject.addProperty("employer_name", namaInstansi.getText().toString());
        jsonObject.addProperty("been_workingfor", Integer.parseInt(Objects.requireNonNull(lamaBekerja.getText().toString())));
        jsonObject.addProperty("employer_address", etAlamatKantor.getText().toString());
        jsonObject.addProperty("employer_number", etPhone.getText().toString());
        jsonObject.addProperty("direct_superiorname", namaAtasan.getText().toString());
        jsonObject.addProperty("occupation", jabatan.getText().toString());
        jsonObject.addProperty("monthly_income", Integer.parseInt(primary_income));
        jsonObject.addProperty("other_income", otherincome);
        jsonObject.addProperty("other_incomesource", etSumberPendapatanLain.getText().toString());

        mPresenter.patchJobEarningData(jsonObject);
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadInfoPekerjaanDanKeuangan(PreferenceRepository user) {

        for (int i=0; i<jobRepo.length; i++) {
            if (jobRepo[i].toLowerCase().equals(user.getFieldToWork().toLowerCase())) {
                spJenisPekerjaan.setSelection(i);
            }
        }

        noIndukPegawai.setText(user.getEmployeeId());

        namaInstansi.setText(user.getEmployerName());

        lamaBekerja.setText(String.valueOf(user.getBeenWorkingFor()));

        etAlamatKantor.setText(user.getEmployerAddress());

        etPhone.setText(user.getEmployerNumber());

        namaAtasan.setText(user.getDirectSuperiorName());

        jabatan.setText(user.getOccupation());

        etPenghasilan.setText(String.format(Locale.getDefault(), "%,d", Integer.parseInt(user.getUserPrimaryIncome())));

        etPendapatanLain.setText(String.format(Locale.getDefault(), "%,d", Integer.parseInt(user.getUserOtherIncome()))) ;

        etSumberPendapatanLain.setText(user.getUserOtherSourceIncome());

    }

    @Override
    public void successUpdateJobEarningData() {
        Intent intent = new Intent(parentActivity(), InformasiKeuanganActivity.class);
        startActivity(intent);
        parentActivity().finish();
    }

    @Override
    public void onValidationSucceeded() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        updateDataProfile();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setMessage("Apakah Anda Yakin Akan Merubah Data?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof Spinner) {
                ((TextView) ((Spinner) view).getSelectedView()).setError(message);
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
