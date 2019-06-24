package com.ayannah.bantenbank.screen.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.util.NumberSeparatorTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormJobAndEarning extends Fragment {

    private FormRegisterOthers fragment = new FormRegisterOthers();

    @BindView(R.id.spJenisPekerjaan)
    Spinner spJenisPekerjaan;

    @BindView(R.id.etGajiBulanan)
    EditText etGajiBulanan;

    @BindView(R.id.etPendapatanLain)
    EditText etPendapatanLain;

    @BindView(R.id.etSumberPendapatanLain)
    EditText etSumberPendaptanLain;

    //spinner
    private String[] pekerjaan = {"Pilih...", "Pemerintahan", "CPNS", "Pegawai Swasta", "Pegawai Pemerintah Nasional", "Pegawai Pemerintah Daerah"};
    private ArrayAdapter<String> mAdapterPekerjaan;

    public FormJobAndEarning(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_job_earning, container, false);
        ButterKnife.bind(this, view);

        mAdapterPekerjaan = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, pekerjaan);
        spJenisPekerjaan.setAdapter(mAdapterPekerjaan);

        NumberSeparatorTextWatcher gajiseparator = new NumberSeparatorTextWatcher(etGajiBulanan);
        NumberSeparatorTextWatcher pendapataLain = new NumberSeparatorTextWatcher(etPendapatanLain);

        etGajiBulanan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etGajiBulanan.addTextChangedListener(gajiseparator);
                }

                if(!hasFocus){
                    etGajiBulanan.removeTextChangedListener(gajiseparator);
                }
            }
        });

        etPendapatanLain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etPendapatanLain.addTextChangedListener(pendapataLain);
                }

                if(!hasFocus){
                    etPendapatanLain.removeTextChangedListener(pendapataLain);
                }
            }
        });

        return view;
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){

        checkEarningUser();

    }

    private void checkEarningUser() {

        String gaji_bulanan = etGajiBulanan.getText().toString().replaceAll("[.,]", "");
        String pendapatan_lain = etPendapatanLain.getText().toString().replaceAll("[.,]", "");
        String sumber_pendapatan_lain = etSumberPendaptanLain.getText().toString().replaceAll("[.,]", "");

        Toast.makeText(getContext(), "gajibulanan: "+gaji_bulanan+", pendaptan: "+pendapatan_lain, Toast.LENGTH_SHORT).show();

//        if(gaji_bulanan.isEmpty() ||
//                pendapatan_lain.isEmpty()){
//
//            Toast.makeText(getContext(), "Pastikan Gaji Bulanan dan Pendaptan lain teriisi.", Toast.LENGTH_SHORT).show();
//            etGajiBulanan.requestFocus();
//
//        }else {
//
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_container, fragment);
//            ft.commit();
//
//        }

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();

    }
}
