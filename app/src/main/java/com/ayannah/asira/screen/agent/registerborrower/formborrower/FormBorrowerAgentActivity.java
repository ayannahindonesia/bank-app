package com.ayannah.asira.screen.agent.registerborrower.formborrower;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class FormBorrowerAgentActivity extends DaggerAppCompatActivity {

    @Inject
    FormBorrowerAgentFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_borrower);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Register");

        FormBorrowerAgentFragment formBorrowerAgentFragment = (FormBorrowerAgentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(formBorrowerAgentFragment == null){

            formBorrowerAgentFragment = mFragment;

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), formBorrowerAgentFragment, R.id.fragment_container);
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
