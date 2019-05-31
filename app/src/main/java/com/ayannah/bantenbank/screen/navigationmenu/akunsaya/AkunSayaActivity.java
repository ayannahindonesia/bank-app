package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class AkunSayaActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    AkunSayaFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Akun saya");

        AkunSayaFragment akunSayaFragment = (AkunSayaFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(akunSayaFragment == null){
            akunSayaFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), akunSayaFragment, R.id.fragment_container);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
