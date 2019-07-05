package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.util.ActivityUtils;
import com.ayannah.bantenbank.util.NumberSeparatorTextWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class InformasiKeuanganActivity extends DaggerAppCompatActivity {

    @Inject
    InformasiKeuanganFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_keuangan);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Pekerjaan & Keuangan");

        InformasiKeuanganFragment informasiKeuanganFragment = (InformasiKeuanganFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(informasiKeuanganFragment == null){
            informasiKeuanganFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), informasiKeuanganFragment, R.id.fragment_container);
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
