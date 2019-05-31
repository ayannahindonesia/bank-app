package com.ayannah.bantenbank.screen.summary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;
import com.ayannah.bantenbank.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SummaryTransactionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pinjaman)
    TextView tvPinjaman;

    @BindView(R.id.tenor)
    TextView tvTenor;

    @BindView(R.id.bunga)
    TextView tvBunga;

    @BindView(R.id.biayaAdmin)
    TextView tvBiayaAdmin;

    @BindView(R.id.angsuranBulanan)
    TextView tvAngsuran;

    @BindView(R.id.saldoPokokPinjaman)
    TextView tvSaldoPokokPinjaman;

    @BindView(R.id.alasan)
    TextView tvAlasan;

    @BindView(R.id.tujuanPinjam)
    TextView tvTujuanPinjam;


    private Unbinder mUnbinder;

    //value purposes
    double pinjaman = 0;
    int tenor = 0;
    double angsuranBulan = 0;
    double saldoPinjaman =0;
    String alasan;
    String tujuan;

    public static final String PINJAMAN = "pinjaman";
    public static final String TENOR = "tenor";
    public static final String BUNGA = "bunga";
    public static final String BIAYA_ADMIN = "biayaadmin";
    public static final String ANGSURAN_BULAN = "angsuranBulan";
    public static final String SALDO_PINJAMAN = "saldoPinjaman";
    public static final String ALASAN = "alasan";
    public static final String TUJUAN = "tujuan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_transaction);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Rincian Peminjaman");

        setValueIntent();

        tvPinjaman.setText(CommonUtils.setRupiahCurrency( (int) pinjaman) );

        tvTenor.setText(String.format("%s tahun (%s bulan)", tenor, tenor*12));

        double calculateBunga = ((pinjaman * 13)/100 ) / (tenor * 12);
        tvBunga.setText(CommonUtils.setRupiahCurrency((int) calculateBunga));

        double calBiayaAdmin = (pinjaman * 1.5)/100;
        tvBiayaAdmin.setText(CommonUtils.setRupiahCurrency((int) calBiayaAdmin));

        double calAngsuran = (pinjaman / (tenor * 12)) + calculateBunga;
        tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(calAngsuran)));

        double saldoPinjaman = pinjaman - (pinjaman / (tenor * 12));
        tvSaldoPokokPinjaman.setText(CommonUtils.setRupiahCurrency((int) Math.floor(saldoPinjaman)));

        tvAlasan.setText(alasan);

        tvTujuanPinjam.setText(tujuan);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setValueIntent() {
        pinjaman = getIntent().getDoubleExtra(PINJAMAN, 0);

        tenor = getIntent().getIntExtra(TENOR, 0);

        angsuranBulan = getIntent().getDoubleExtra(ANGSURAN_BULAN, 0);

        saldoPinjaman = getIntent().getDoubleExtra(SALDO_PINJAMAN, 0);

        alasan = getIntent().getStringExtra(ALASAN);

        tujuan = getIntent().getStringExtra(TUJUAN);



    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        Intent intent = new Intent(this, VerificationOTPActivity.class);
        intent.putExtra("purpose", "pinjaman");
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
