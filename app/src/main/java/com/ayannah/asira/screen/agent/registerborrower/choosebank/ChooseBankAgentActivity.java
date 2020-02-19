package com.ayannah.asira.screen.agent.registerborrower.choosebank;

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
import dagger.Binds;
import dagger.android.support.DaggerAppCompatActivity;

public class ChooseBankAgentActivity extends DaggerAppCompatActivity {

    @Inject
    ChooseBankAgentFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_activity_choose_bank_v2); //agent_activity_choose_bank
        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        ChooseBankAgentFragment chooseBankAgentFragment = (ChooseBankAgentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (chooseBankAgentFragment == null) {
            chooseBankAgentFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), chooseBankAgentFragment, R.id.fragment_container);
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
