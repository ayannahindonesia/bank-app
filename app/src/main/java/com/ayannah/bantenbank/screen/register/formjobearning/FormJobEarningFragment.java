package com.ayannah.bantenbank.screen.register.formjobearning;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherActivity;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;
import com.ayannah.bantenbank.util.NumberSeparatorTextWatcher;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormJobEarningFragment extends BaseFragment implements Validator.ValidationListener {

    @Select(message = "Pilih Jenis Pekerjaan")
    @BindView(R.id.spJenisPekerjaan)
    Spinner spJenisPekerjaan;

    @NotEmpty(message = "Masukan Gaji Anda", trim = true)
    @BindView(R.id.etGajiBulanan)
    EditText etGajiBulanan;

    @BindView(R.id.etPendapatanLain)
    EditText etPendapatanLain;

    @BindView(R.id.etSumberPendapatanLain)
    EditText etSumberPendaptanLain;

    @NotEmpty(message = "Masukan Nomor Induk Pegawai Anda", trim = true)
    @BindView(R.id.etEmployeeID)
    EditText etEmployeeID;

    @NotEmpty(message = "Masukan Nama Perusahaan\nTempat Anda Bekerja", trim = true)
    @BindView(R.id.etCompanyName)
    EditText etCompanyName;

    @NotEmpty(message = "Masukan Lama Anda Bekerja", trim = true)
    @BindView(R.id.etLamaBekerja)
    EditText etLamaBekerja;

    @NotEmpty(message = "Masukan Alamat Perusahaan\nTempat Anda Bekerja", trim = true)
    @BindView(R.id.etAlamatKantor)
    EditText etAlamatKantor;

    @NotEmpty(message = "Masukan No Telpon Perusahaan\nTempat Anda Bekerja", trim = true)
    @BindView(R.id.etCompanyPhone)
    EditText etCompanyPhone;

    @NotEmpty(message = "Masukan Nama Atasan Anda", trim = true)
    @BindView(R.id.etSpvName)
    EditText etSpvName;

    @NotEmpty(message = "Masukan Jabatan Anda", trim = true)
    @BindView(R.id.etJobTitle)
    EditText etJobTitle;

    private Validator validator;

    //spinner
    private String[] pekerjaan = {"Pilih...", "Pemerintahan", "CPNS", "Pegawai Swasta", "Pegawai Pemerintah Nasional", "Pegawai Pemerintah Daerah"};
    private ArrayAdapter<String> mAdapterPekerjaan;

    @Inject
    public FormJobEarningFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_form_job_earning;
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

        mAdapterPekerjaan = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, pekerjaan);
        spJenisPekerjaan.setAdapter(mAdapterPekerjaan);

//        NumberSeparatorTextWatcher gajiseparator = new NumberSeparatorTextWatcher(etGajiBulanan);
        NumberSeparatorTextWatcher pendapataLain = new NumberSeparatorTextWatcher(etPendapatanLain);

        etGajiBulanan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String input = s.toString();

                if (!input.isEmpty()) {

                    input = input.replace(",", "");

                    DecimalFormat format = new DecimalFormat("#,###,###");
                    String newPrice = format.format(Double.parseDouble(input));


                    etGajiBulanan.removeTextChangedListener(this); //To Prevent from Infinite Loop

                    etGajiBulanan.setText(newPrice);
                    etGajiBulanan.setSelection(newPrice.length()); //Move Cursor to end of String

                    etGajiBulanan.addTextChangedListener(this);
                }

            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });

        etPendapatanLain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etPendapatanLain.addTextChangedListener(pendapataLain);
                }
            }
        });
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){

        validator.validate();

    }

    private void checkEarningUser() {

        String gaji_bulanan = etGajiBulanan.getText().toString().replaceAll("[.,]", "");
        String pendapatan_lain = etPendapatanLain.getText().toString().replaceAll("[.,]", "");
        String sumber_pendapatan_lain = etSumberPendaptanLain.getText().toString().replaceAll("[.,]", "");
        if (pendapatan_lain.equals("")) {
            pendapatan_lain = "0";
        }

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        bundle.putString(FormOtherFragment.OCCUPATION, spJenisPekerjaan.getSelectedItem().toString());
        bundle.putString(FormOtherFragment.EMPLOYEE_ID, etEmployeeID.getText().toString());
        bundle.putString(FormOtherFragment.COMPANY_NAME, etCompanyName.getText().toString());
        bundle.putString(FormOtherFragment.WORK_PERIOD, etLamaBekerja.getText().toString());
        bundle.putString(FormOtherFragment.COMPANY_ADDRESS, etAlamatKantor.getText().toString());
        bundle.putString(FormOtherFragment.COMPANY_PHONE, etCompanyPhone.getText().toString());
        bundle.putString(FormOtherFragment.SUPERVISOR, etSpvName.getText().toString());
        bundle.putString(FormOtherFragment.JOB_TITLE, etJobTitle.getText().toString());
        bundle.putString(FormOtherFragment.SALARY, gaji_bulanan);
        bundle.putString(FormOtherFragment.OTHER_INCOME, pendapatan_lain);
        bundle.putString(FormOtherFragment.OTHER_INCOME_SOURCE, sumber_pendapatan_lain);

        Intent intent = new Intent(parentActivity(), FormOtherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onValidationSucceeded() {
        checkEarningUser();
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
                view.setFocusableInTouchMode(true);
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            } else if (view instanceof TextView) {
                ((TextView) view).setError(message);
                view.requestFocus();
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
