package com.ayannah.asira.screen.agent.services;

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

public class ListServicesAgentActivity extends DaggerAppCompatActivity {

    public static final String BANK_ID = "bank_id";

    @Inject
    ListServicesAgentFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_activity_service_list);
        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        ListServicesAgentFragment listServicesAgentFragment = (ListServicesAgentFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(listServicesAgentFragment == null){
            listServicesAgentFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), listServicesAgentFragment, R.id.fragment_container);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
