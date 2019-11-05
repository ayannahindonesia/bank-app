package com.ayannah.asira.screen.borrower.notifpage;

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

public class NotifPageActivity extends DaggerAppCompatActivity {

    @Inject
    NotifPageFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbiner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        mUnbiner = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notifikasi");

        NotifPageFragment notifPageFragment = (NotifPageFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(notifPageFragment == null){
            notifPageFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), notifPageFragment, R.id.fragment_container);
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
        mUnbiner.unbind();
    }
}
