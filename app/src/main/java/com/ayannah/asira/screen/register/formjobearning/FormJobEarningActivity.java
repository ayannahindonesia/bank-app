package com.ayannah.asira.screen.register.formjobearning;

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

public class FormJobEarningActivity extends DaggerAppCompatActivity {

    @Inject
    FormJobEarningFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnibder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_job_and_earning);
        mUnibder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Register");

        FormJobEarningFragment formJobEarningFragment = (FormJobEarningFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(formJobEarningFragment == null){
            formJobEarningFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), formJobEarningFragment, R.id.fragment_container);
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
        mUnibder.unbind();
    }
}
