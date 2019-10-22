package com.ayannah.asira.screen.earninginfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class EarningActivity extends DaggerAppCompatActivity {

    public static final String ID_SERVICE = "IDSERVICE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    EarningFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Info Penghasilan");

        EarningFragment earningFragment = (EarningFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(earningFragment == null){
            earningFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), earningFragment, R.id.fragment_container);
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
