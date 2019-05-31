package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.util.NumberSeparatorTextWatcher;

import javax.inject.Inject;

import butterknife.BindView;

public class InformasiKeuanganFragment extends BaseFragment implements InformasiKeuanganContract.View {

    @Inject
    InformasiKeuanganContract.Presenter mPresenter;

    @BindView(R.id.spJenisPekerjaan)
    Spinner spJenisPekerjaan;

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

    @Override
    public void showErrorMessage(String message) {

    }
}
