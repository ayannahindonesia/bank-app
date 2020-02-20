package com.ayannah.asira.screen.otpphone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
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
        setContentView(R.layout.common_activity_before_login);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupToolbar();

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

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Hide the title
        getSupportActionBar().setTitle(null);
        // Set onClickListener to customView
        TextView tvSkip = (TextView) findViewById(R.id.toolbar_skip);
        tvSkip.setCompoundDrawables(getResources().getDrawable(R.drawable.agen_btn), null, null, null);
        tvSkip.setText("Kembali");
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tvHelp = (TextView) findViewById(R.id.toolbar_help);
        tvHelp.setVisibility(View.GONE);
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
            case "REGISTER_BORROWER":

                alertCancel("Tunda verifikasi pendaftaran peminjam?");
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
//                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(intent);
                    VerificationOTPFragment.countDownTimer.cancel();
                    finish();
                } else if (getIntent().getStringExtra(PURPOSES).equals("post_pinjaman")) {
                    Toast.makeText(VerificationOTPActivity.this, "Silahkan masuk ke halaman 'Pinjaman Saya'\nuntuk aktivasi pengajuan Anada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), BorrowerLandingPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if(getIntent().getStringExtra(PURPOSES).equals("REGISTER_BORROWER")) {
                    Intent intent = new Intent(getBaseContext(), LPAgentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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
        VerificationOTPFragment.countDownTimer.cancel();

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
