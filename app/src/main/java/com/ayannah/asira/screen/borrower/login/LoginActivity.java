package com.ayannah.asira.screen.borrower.login;

import android.os.Bundle;

import com.ayannah.asira.R;
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
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);

        LoginFragment loginFragment = (LoginFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(loginFragment == null){
            loginFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.fragment_container);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
