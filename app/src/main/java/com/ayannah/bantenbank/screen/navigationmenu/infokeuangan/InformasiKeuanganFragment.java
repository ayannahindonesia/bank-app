package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.ayannah.bantenbank.util.NumberSeparatorTextWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InformasiKeuanganFragment extends BaseFragment implements InformasiKeuanganContract.View {

    @Inject
    InformasiKeuanganContract.Presenter mPresenter;

    @BindView(R.id.spJenisPekerjaan)
    Spinner spJenisPekerjaan;

    @BindView(R.id.noPegawai)
    EditText noIndukPegawai;

    @BindView(R.id.namaInstansi)
    EditText namaInstansi;

    @BindView(R.id.lamaBekerja)
    EditText lamaBekerja;

    @BindView(R.id.etAlamatKantor)
    EditText etAlamatKantor;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.namaAtasan)
    EditText namaAtasan;

    @BindView(R.id.jabatan)
    EditText jabatan;



    @BindView(R.id.etPenghasilan)
    EditText etPenghasilan;

    @BindView(R.id.etPendapatanLain)
    EditText etPendapatanLain;

    @BindView(R.id.etSumberPendapatanLain)
    EditText etSumberPendapatanLain;

    private String[] jobRepo = {"Pemerintahan", "CPNS", "Pegawai Swasta", "Kepala Daerah", "Pegawai Pemerintah Nasional", "Pegawai Pemerintah Daerah"};

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

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, jobRepo);
        spJenisPekerjaan.setAdapter(mAdapter);

        NumberSeparatorTextWatcher penghasilanNumberSeparator = new NumberSeparatorTextWatcher(etPenghasilan);
        etPenghasilan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etPenghasilan.addTextChangedListener(penghasilanNumberSeparator);
                }else {
                    etPenghasilan.removeTextChangedListener(penghasilanNumberSeparator);
                }
            }
        });

        NumberSeparatorTextWatcher pendapatanLainNumberSeparator = new NumberSeparatorTextWatcher(etPendapatanLain);
        etPendapatanLain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etPendapatanLain.addTextChangedListener(pendapatanLainNumberSeparator);
                }else {
                    etPendapatanLain.removeTextChangedListener(pendapatanLainNumberSeparator);
                }
            }
        });

    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        Toast.makeText(parentActivity(), "submit", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadInfoPekerjaanDanKeuangan(UserProfile user) {

        noIndukPegawai.setText(user.getEmployerNumber());

        namaInstansi.setText(user.getDepartment());

        lamaBekerja.setText(String.valueOf(user.getBeenWorkingfor()));

        etAlamatKantor.setText(user.getEmployerAddress());

        etPhone.setText(user.getPhone());

        namaAtasan.setText(user.getDirectSuperiorname());

        jabatan.setText(user.getEmployerName());

        etPenghasilan.setText(String.valueOf(user.getMonthlyIncome()));

        etPendapatanLain.setText(String.valueOf(user.getOtherIncome()));

        etSumberPendapatanLain.setText(user.getOtherIncomesource());

    }
}
