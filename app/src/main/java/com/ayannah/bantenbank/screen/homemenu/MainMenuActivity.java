package com.ayannah.bantenbank.screen.homemenu;

import android.os.Bundle;

import com.ayannah.bantenbank.R;

import com.ayannah.bantenbank.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class MainMenuActivity extends DaggerAppCompatActivity {

    @Inject
    MainMenuFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mUnbinder = ButterKnife.bind(this);

        MainMenuFragment mainMenuFragment = (MainMenuFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(mainMenuFragment == null){
            mainMenuFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainMenuFragment, R.id.fragment_container);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
