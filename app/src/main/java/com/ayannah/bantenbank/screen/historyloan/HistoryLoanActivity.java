package com.ayannah.bantenbank.screen.historyloan;

import android.os.Bundle;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class HistoryLoanActivity extends DaggerAppCompatActivity {

    @Inject
    HistoryLoanFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_loan);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Pinjaman saya");

        HistoryLoanFragment historyLoanFragment = (HistoryLoanFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(historyLoanFragment == null){
            historyLoanFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), historyLoanFragment, R.id.fragment_container);
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
