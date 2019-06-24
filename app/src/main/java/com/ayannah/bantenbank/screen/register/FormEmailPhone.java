package com.ayannah.bantenbank.screen.register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.UserRegister;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormEmailPhone extends Fragment {

    @BindView(R.id.regist_email)
    EditText email;

    @BindView(R.id.regist_phone)
    EditText phone;

    @BindView(R.id.regist_pass)
    EditText pass;

    @BindView(R.id.regist_pass_retype)
    EditText passRetype;

    public FormEmailPhone(){}

    FormBorrowerFragment fragemnt = new FormBorrowerFragment();

    RegisterListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (RegisterListener)context;
    }

    public void onDataPass(UserRegister register){
        listener.onDataPass(register);
    }

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
//        getActivity().finish()

        UserRegister register = new UserRegister();
        register.setEmail(email.getText().toString().trim());
        register.setPhone(phone.getText().toString().trim());
        register.setPassword(pass.getText().toString().trim());

        listener.onDataPass(register);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragemnt);
        ft.commit();

    }
}
