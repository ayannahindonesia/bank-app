package com.ayannah.asira.screen.agent.loginagent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginAgentActivity extends DaggerAppCompatActivity {

    @Inject
    LoginAgentFragment mFragment;



    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_login_agent);
        mUnbinder = ButterKnife.bind(this);

        LoginAgentFragment loginAgentFragment = (LoginAgentFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (loginAgentFragment == null) {
            loginAgentFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginAgentFragment, R.id.fragment_container);
        }
    }

    @OnClick(R.id.toolbar_back)
    void onClickBack(){

        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
