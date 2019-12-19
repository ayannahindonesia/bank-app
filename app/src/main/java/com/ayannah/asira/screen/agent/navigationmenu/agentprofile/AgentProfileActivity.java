package com.ayannah.asira.screen.agent.navigationmenu.agentprofile;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class AgentProfileActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    AgentProfileFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Akun saya");

        AgentProfileFragment agentProfileFragment = (AgentProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (agentProfileFragment == null) {
            agentProfileFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), agentProfileFragment, R.id.fragment_container);
        }
    }
}
