package com.ayannah.bantenbank.screen.register;

import android.os.Bundle;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.UserRegister;
import com.ayannah.bantenbank.screen.register.choosebank.ChooseBank;
import com.ayannah.bantenbank.util.ActivityUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class RegisterActivity extends DaggerAppCompatActivity implements RegisterListener {

//    ChooseBank chooseBank = new ChooseBank();

    @Inject
    ChooseBank mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Pendaftaran");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.fragment_container, chooseBank);
//        ft.addToBackStack(null);
//        ft.commit();
        ChooseBank chooseBank = (ChooseBank)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(chooseBank == null){
            chooseBank = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), chooseBank, R.id.fragment_container);

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
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onDataPass(UserRegister register) {


        Log.d("register", "accountBank: "+register.getBankAccountnumber());
        Log.d("register", "email: "+register.getEmail());
        Log.d("register", "phoneNum: "+register.getPhone());
        Log.d("register", "pass: "+register.getPassword());

    }
}
