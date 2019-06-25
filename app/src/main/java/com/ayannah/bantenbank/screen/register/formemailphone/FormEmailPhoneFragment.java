package com.ayannah.bantenbank.screen.register.formemailphone;

import android.content.Context;
import android.content.Intent;
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
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.UserRegister;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormEmailPhoneFragment extends BaseFragment {

    @BindView(R.id.regist_email)
    EditText email;

    @BindView(R.id.regist_phone)
    EditText phone;

    @BindView(R.id.regist_pass)
    EditText pass;

    @BindView(R.id.regist_pass_retype)
    EditText passRetype;

    @Inject
    public FormEmailPhoneFragment(){}


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_email_phone;
    }

    @Override
    protected void initView(Bundle state) {

    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){

        Intent form = new Intent(parentActivity(), FormBorrowerActivity.class);
        form.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(form);
    }
}
