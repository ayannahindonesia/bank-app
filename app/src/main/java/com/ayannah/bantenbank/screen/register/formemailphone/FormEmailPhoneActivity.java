package com.ayannah.bantenbank.screen.register.formemailphone;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.register.choosebank.ChooseBankFragment;
import com.ayannah.bantenbank.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class FormEmailPhoneActivity extends DaggerAppCompatActivity {

    @Inject
    FormEmailPhoneFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_email_phone);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Register");

        FormEmailPhoneFragment formEmailPhoneFragment = (FormEmailPhoneFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(formEmailPhoneFragment == null){
            formEmailPhoneFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), formEmailPhoneFragment, R.id.fragment_container);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
