package com.ayannah.bantenbank.screen.register.formothers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class FormOtherActivity extends DaggerAppCompatActivity {

    @Inject
    FormOtherFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_other);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Register");

        FormOtherFragment formOtherFragment = (FormOtherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(formOtherFragment == null){
            formOtherFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), formOtherFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
