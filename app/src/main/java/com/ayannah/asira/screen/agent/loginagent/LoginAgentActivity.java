package com.ayannah.asira.screen.agent.loginagent;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginAgentActivity extends DaggerAppCompatActivity {

    @Inject
    LoginAgentFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);

        LoginAgentFragment loginAgentFragment = (LoginAgentFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (loginAgentFragment == null) {
            loginAgentFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginAgentFragment, R.id.fragment_container);
        }
    }
}
