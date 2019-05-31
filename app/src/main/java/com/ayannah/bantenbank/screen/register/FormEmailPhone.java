package com.ayannah.bantenbank.screen.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormEmailPhone extends Fragment {

    public FormEmailPhone(){}

    FormBorrowerIdentity fragemnt = new FormBorrowerIdentity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_phone, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){

//        Intent verification = new Intent(getContext(), VerificationOTPActivity.class);
//        verification.putExtra("purpose", "regist");
//        startActivity(verification);
//        getActivity().finish();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragemnt);
        ft.commit();

    }
}
