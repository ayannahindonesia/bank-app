package com.ayannah.bantenbank.screen.otpphone;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.custom.PinEntryEditText;
import com.ayannah.bantenbank.data.local.PreferenceDataSource;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.dialog.DialogSuccess;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.ayannah.bantenbank.screen.login.LoginActivity;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherContract;
import com.ayannah.bantenbank.util.ActivityUtils;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class VerificationOTPActivity extends DaggerAppCompatActivity {

    public final static String PURPOSES = "purpose";
    public final static String IDLOAN = "idloan";


    private Unbinder mUnbinder;
    public String phoneRegister = "";

    @Inject
    VerificationOTPFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Verifikasi");

        VerificationOTPFragment verificationOTPFragment = (VerificationOTPFragment) getSupportFragmentManager()
        .findFragmentById(R.id.fragment_container);
        if(verificationOTPFragment == null){
            verificationOTPFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), verificationOTPFragment, R.id.fragment_container);
        }


    }

    @Override
    public void onBackPressed() {

        //action when user trying click back button in otp activity

        if(getIntent().getStringExtra(PURPOSES).equals("resubmit_loan")){

            alertCancel(getResources().getString(R.string.resubmit_loan_alert));

        }else if(getIntent().getStringExtra(PURPOSES).equals("pinjaman")){

            alertCancel(getResources().getString(R.string.submit_loan_alert));
        }else if (getIntent().getStringExtra(PURPOSES).equals("regist")) {

            alertCancel(getResources().getString(R.string.otp_regist_alert));
        } else if (getIntent().getStringExtra(PURPOSES).equals("resubmit_regist")) {

            alertCancel(getResources().getString(R.string.resubmit_regist_alert));
        } else {
            super.onBackPressed();
        }
    }

    private void alertCancel(String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (getIntent().getStringExtra(PURPOSES).equals("regist")) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
        alert.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
