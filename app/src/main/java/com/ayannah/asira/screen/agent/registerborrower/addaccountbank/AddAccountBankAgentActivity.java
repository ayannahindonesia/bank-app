package com.ayannah.asira.screen.agent.registerborrower.addaccountbank;

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

public class AddAccountBankAgentActivity extends DaggerAppCompatActivity {

    @Inject
    AddAccountBankAgentFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_bank);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Register");

        AddAccountBankAgentFragment addAccountBankAgentFragment = (AddAccountBankAgentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(addAccountBankAgentFragment == null){
            addAccountBankAgentFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addAccountBankAgentFragment, R.id.fragment_container);
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
