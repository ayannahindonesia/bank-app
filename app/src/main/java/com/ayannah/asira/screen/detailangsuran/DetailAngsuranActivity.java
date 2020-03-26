package com.ayannah.asira.screen.detailangsuran;

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

public class DetailAngsuranActivity extends DaggerAppCompatActivity {

    @Inject
    DetailAngsuranFragment mFragment;

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
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detail Angsuran");

        DetailAngsuranFragment detailAngsuranFragment = (DetailAngsuranFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(detailAngsuranFragment == null){
            detailAngsuranFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), detailAngsuranFragment, R.id.fragment_container);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
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
