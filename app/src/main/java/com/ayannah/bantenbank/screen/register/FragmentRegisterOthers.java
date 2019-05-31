package com.ayannah.bantenbank.screen.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentRegisterOthers extends Fragment {

    @BindView(R.id.spHubungan)
    Spinner spHubungan;

    private String[] siblings = {"Saudara", "Teman", "Keluarga Kandung"};

    public FragmentRegisterOthers(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_information_lainlain, container, false);
        ButterKnife.bind(this, view);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), R.layout.item_custom_spinner, siblings);
        spHubungan.setAdapter(mAdapter);

        return view;
    }


    @OnClick(R.id.buttonNext)
    void onClickNext(){

        Intent verification = new Intent(getContext(), VerificationOTPActivity.class);
        verification.putExtra("purpose", "regist");
        startActivity(verification);
        getActivity().finish();

    }


}
