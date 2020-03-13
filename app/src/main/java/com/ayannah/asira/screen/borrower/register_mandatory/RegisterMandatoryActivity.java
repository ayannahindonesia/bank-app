package com.ayannah.asira.screen.borrower.register_mandatory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.bantuan.BantuanActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class RegisterMandatoryActivity extends DaggerAppCompatActivity {

    @Inject
    RegisterMandatoryFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_before_login);
        mUnbinder = ButterKnife.bind(this);

        setupToolbar();

        RegisterMandatoryFragment registerMandatoryFragment = (RegisterMandatoryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(registerMandatoryFragment == null){
            registerMandatoryFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), registerMandatoryFragment, R.id.fragment_container);
        }
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Hide the title
        getSupportActionBar().setTitle(null);
        // Set onClickListener to customView
        TextView tvSkip = (TextView) findViewById(R.id.toolbar_skip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), BorrowerLandingPage.class);
                startActivity(intent);
                finish();
            }
        });

        TextView tvHelp = (TextView) findViewById(R.id.toolbar_help);
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BantuanActivity.class);
                startActivity(intent);
            }
        });
    }
}
