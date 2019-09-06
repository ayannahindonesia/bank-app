package com.ayannah.bantenbank.screen.summary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;
import com.ayannah.bantenbank.util.ActivityUtils;
import com.ayannah.bantenbank.util.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class SummaryTransactionActivity extends DaggerAppCompatActivity {

    @Inject
    SummaryTransactionFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    public static final String PINJAMAN = "pinjaman";
    public static final String TENOR = "tenor";
    public static final String ANGSURAN_BULAN = "angsuranBulan";
    public static final String SALDO_PINJAMAN = "saldoPinjaman";
    public static final String ALASAN = "alasan";
    public static final String TUJUAN = "tujuan";
    public static final String PRODUK = "produk";
    public static final String ADMIN = "admin";
    public static final String INTEREST = "interest";
    public static final String LAYANAN = "layanan";
    public static final String PRODUCTID = "productid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Rincian Peminjaman");

        SummaryTransactionFragment summaryTransactionActivity = (SummaryTransactionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if(summaryTransactionActivity == null){
            summaryTransactionActivity = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), summaryTransactionActivity, R.id.fragment_container);
        }


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
