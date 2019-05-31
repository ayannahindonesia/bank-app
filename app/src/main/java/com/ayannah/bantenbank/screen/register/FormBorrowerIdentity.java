package com.ayannah.bantenbank.screen.register;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormBorrowerIdentity extends Fragment {

//    private FragmentRegisterOthers fragment = new FragmentRegisterOthers();
    private FormJobAndEarning fragment = new FormJobAndEarning();


    //datepicker
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat sdf;

    @BindView(R.id.dateBirth)
    TextView dateBirth;

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
    private String[] kota = {"Jakarta Utara", "Jakarta Pusat", "Jakarta Timur", "Jakarta Selatan", "Jakarta Barat"};

    public FormBorrowerIdentity(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrower_identity, container, false);
        ButterKnife.bind(this, view);

        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, educationRepo);
        spCollageLevel.setAdapter(mAdapter);
        spPendidikan.setAdapter(mAdapter);

        ArrayAdapter<String> mAdapterPerkawinan = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, statusPerkawinan);
        spPerkawinan.setAdapter(mAdapterPerkawinan);

        ArrayAdapter<String> mAdapterTanggungan = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, tanggungan);
        spTanggungan.setAdapter(mAdapterTanggungan);

        ArrayAdapter<String> mAdapterKota = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, kota);
        spKota.setAdapter(mAdapterKota);

        ArrayAdapter<String> mAdapterStatusHome = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, statusTempatTinggal);
        spStatusHome.setAdapter(mAdapterStatusHome);

        loadProvinsi();
        loadKota();
        loadKec();
        loadKel();

        return view;
    }

    private void loadKel() {

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

        ArrayAdapter<String> mAdapterProvince =  new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, provinsiRepo);
        spKelurahan.setAdapter(mAdapterProvince);

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

        ArrayAdapter<String> mAdapterKec =  new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, provinsiRepo);
        spKecamatan.setAdapter(mAdapterKec);

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

        ArrayAdapter<String> mAdapterKota =  new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, provinsiRepo);
        spKota.setAdapter(mAdapterKota);

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

        ArrayAdapter<String> mAdapterProvince =  new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, provinsiRepo);
        spProvinsi.setAdapter(mAdapterProvince);
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();

    }

    @OnClick(R.id.dateBirth)
    void onClickDateBirth(){
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, month, dayOfMonth);

                dateBirth.setText(sdf.format(newCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
}
