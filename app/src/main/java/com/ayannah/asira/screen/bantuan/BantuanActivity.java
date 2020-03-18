package com.ayannah.asira.screen.bantuan;

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

public class BantuanActivity extends DaggerAppCompatActivity {

    @Inject
    BantuanFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Bantuan");

        BantuanFragment bantuanFragment = (BantuanFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(bantuanFragment == null){
            bantuanFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), bantuanFragment, R.id.fragment_container);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 0){

            getSupportFragmentManager().popBackStack();

        }else {

            finish();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
