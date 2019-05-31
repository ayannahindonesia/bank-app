package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ayannah.bantenbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DataPendukungActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.spHubungan)
    Spinner spHub;

    private String[] siblings = {"Saudara Kandung", "Suami/Istri", "Saudara"};

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pendukung);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Data Pendukung");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.item_custom_spinner, siblings);
        spHub.setAdapter(mAdapter);


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
