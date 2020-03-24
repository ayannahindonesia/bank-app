package com.ayannah.asira.screen.borrower.register_personal_info;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class RegisterPersonalInfoActivity extends DaggerAppCompatActivity {

    @Inject
    RegisterPersonalInfoFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);

        RegisterPersonalInfoFragment registerPersonalInfoFragment = (RegisterPersonalInfoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(registerPersonalInfoFragment == null){
            registerPersonalInfoFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), registerPersonalInfoFragment, R.id.fragment_container);
        }
    }
}
