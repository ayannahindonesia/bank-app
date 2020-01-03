package com.ayannah.asira.screen.agent.lpagent;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class LPAgentActivity extends DaggerAppCompatActivity {

    @Inject
    LPAgentFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_landing_page);
        mUnbinder = ButterKnife.bind(this);

        LPAgentFragment lpAgentFragment = (LPAgentFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(lpAgentFragment == null){
            lpAgentFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), lpAgentFragment, R.id.fragment_container);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
