package com.ayannah.asira.screen.navigationmenu.akunsaya;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

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

        AkunSayaFragment akunSayaFragment = (AkunSayaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (akunSayaFragment == null) {
            akunSayaFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), akunSayaFragment, R.id.fragment_container);
        }
        // ATTENTION: This was auto-generated to handle app links.
        handleIntentAppLinks();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntentAppLinks();
    }

    private void handleIntentAppLinks() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            String recipeId = appLinkData.getLastPathSegment();
            Uri appData = Uri.parse("content://com.ayannah.asira/recipe/").buildUpon()
                    .appendPath(recipeId).build();
            showIntent(appData);
        }
    }

    private void showIntent(Uri appData) {
        Toast.makeText(this, appData.getLastPathSegment(), Toast.LENGTH_LONG).show();
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
