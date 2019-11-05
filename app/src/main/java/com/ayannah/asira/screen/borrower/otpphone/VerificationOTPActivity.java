package com.ayannah.asira.screen.borrower.otpphone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.borrower.homemenu.MainMenuActivity;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.util.ActivityUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

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

        switch (getIntent().getStringExtra(PURPOSES)) {
            case "resubmit_loan":

                alertCancel(getResources().getString(R.string.resubmit_loan_alert));

                break;
            case "pinjaman":

                alertCancel(getResources().getString(R.string.submit_loan_alert));
                break;
            case "regist":

                alertCancel(getResources().getString(R.string.otp_regist_alert));
                break;
            case "resubmit_regist":

                alertCancel(getResources().getString(R.string.resubmit_regist_alert));
                break;
            case "post_pinjaman":

                alertCancel(getResources().getString(R.string.resubmit_regist_alert));
                break;
            default:
                super.onBackPressed();
                break;
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
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (getIntent().getStringExtra(PURPOSES).equals("post_pinjaman")) {
                    Toast.makeText(VerificationOTPActivity.this, "Silahkan masuk ke halaman 'Pinjaman Saya'\nuntuk aktivasi pengajuan Anada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        switch (getIntent().getStringExtra(PURPOSES)) {
            case "resubmit_loan":

                alertCancel(getResources().getString(R.string.resubmit_loan_alert));

                break;
            case "pinjaman":

                alertCancel(getResources().getString(R.string.submit_loan_alert));
                break;
            case "regist":

                alertCancel(getResources().getString(R.string.otp_regist_alert));
                break;
            case "resubmit_regist":

                alertCancel(getResources().getString(R.string.resubmit_regist_alert));
                break;
            case "post_pinjaman":

                alertCancel(getResources().getString(R.string.resubmit_regist_alert));
                break;
        }
//        finish();
        return super.onSupportNavigateUp();
    }
}
