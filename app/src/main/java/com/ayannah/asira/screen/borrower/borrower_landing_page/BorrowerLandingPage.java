package com.ayannah.asira.screen.borrower.borrower_landing_page;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class BorrowerLandingPage extends DaggerAppCompatActivity {

    @Inject
    BorrowerLandingFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_borrower_landing_page);
        mUnbinder = ButterKnife.bind(this);

        BorrowerLandingFragment borrowerLandingFragment = (BorrowerLandingFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(borrowerLandingFragment == null){
            borrowerLandingFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), borrowerLandingFragment, R.id.fragment_container);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
