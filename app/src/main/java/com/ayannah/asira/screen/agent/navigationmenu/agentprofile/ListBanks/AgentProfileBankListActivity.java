package com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks;

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

public class AgentProfileBankListActivity extends DaggerAppCompatActivity {

    @Inject
    AgentProfileBankListFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnibder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        mUnibder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Daftar Bank");

        AgentProfileBankListFragment agentProfileBankListFragment = (AgentProfileBankListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(agentProfileBankListFragment == null){
            agentProfileBankListFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), agentProfileBankListFragment, R.id.fragment_container);
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
