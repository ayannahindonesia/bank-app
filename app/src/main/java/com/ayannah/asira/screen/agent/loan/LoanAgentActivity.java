package com.ayannah.asira.screen.agent.loan;

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

public class LoanAgentActivity extends DaggerAppCompatActivity {

    public static final String IDSERVICE = "IDSERVICE";
    public static final String IDBANK = "IDBANK";

    @Inject
    LoanAgentFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Loan");

        LoanAgentFragment loanFragment = (LoanAgentFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(loanFragment == null){
            loanFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loanFragment, R.id.fragment_container);
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
        mUnbinder.unbind();
    }
}
