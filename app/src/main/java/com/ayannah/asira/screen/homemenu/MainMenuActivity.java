package com.ayannah.asira.screen.homemenu;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ayannah.asira.R;

import com.ayannah.asira.service.CheckRepaymentLoanService;
import com.ayannah.asira.util.ActivityUtils;

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
