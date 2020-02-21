package com.ayannah.asira.screen.borrower.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.borrower.register_mandatory.RegisterMandatoryActivity;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity {

    @Inject
    LoginFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_before_login);
        mUnbinder = ButterKnife.bind(this);

        setupToolBar();

        LoginFragment loginFragment = (LoginFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(loginFragment == null){
            loginFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.fragment_container);
        }

    }

    private void setupToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Hide the title
        getSupportActionBar().setTitle(null);
        // Set onClickListener to customView
        TextView tvSkip = (TextView) findViewById(R.id.toolbar_skip);
        tvSkip.setText("Kembali");
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("hasTop")) {
                    Intent intent = new Intent(getBaseContext(), RegisterMandatoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    getIntent().removeExtra("hasTop");
                } else {
                    onBackPressed();
                }
            }
        });

        TextView tvHelp = (TextView) findViewById(R.id.toolbar_help);
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "help",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (getIntent().hasExtra("hasTop")) {
            Intent intent = new Intent(getBaseContext(), RegisterMandatoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            getIntent().removeExtra("hasTop");
        } else {
            super.onBackPressed();
        }
    }
}
