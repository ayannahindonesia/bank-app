package com.ayannah.bantenbank.screen.detailloan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.data.model.Loans.FeesItem;
import com.ayannah.bantenbank.util.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class DetailTransaksiActivity extends DaggerAppCompatActivity implements DetailTransaksiContract.View  {

    public static final String ID_LOAN = "idloan";

    @Inject
    DetailTransaksiContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    String id_loan;

    @BindView(R.id.noPeminjaman)
    TextView noPeminjaman;

    @BindView(R.id.dateCreated)
    TextView dateCreated;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.tujuan)
    TextView tujuan;

    @BindView(R.id.detailTujuan)
    TextView detailTujuan;

    @BindView(R.id.jumlahPinjaman)
    TextView jumlahPinjaman;

    @BindView(R.id.tenor)
    TextView tenor;

    @BindView(R.id.interest)
    TextView interest;

    @BindView(R.id.fees)
    TextView fees;

    @BindView(R.id.angsuran)
    TextView angsuran;

    @BindView(R.id.totalBiaya)
    TextView totalBiaya;

    private Unbinder mUnbinder;

    int admin = 0;
    int calculateTotalBiaya = 0;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getInformationLoan(id_loan);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Detil Pinjaman");

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadAllInformation(DataItem dataItem) {

        noPeminjaman.setText(String.valueOf(dataItem.getId()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat sdfUsed = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault());
        Date getDate = new Date();
        try {
            getDate = sdf.parse(dataItem.getCreatedTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateCreated.setText(sdfUsed.format(getDate));

        status.setText(dataItem.getStatus());

        tujuan.setText(dataItem.getLoanIntention());

        detailTujuan.setText(dataItem.getIntentionDetails());

        jumlahPinjaman.setText(CommonUtils.setRupiahCurrency(dataItem.getLoanAmount()));

        tenor.setText(String.format("%s Bulan", dataItem.getInstallment()));

        double calInterest = ((double)dataItem.getLoanAmount() * 1.5 /100);
        interest.setText(CommonUtils.setRupiahCurrency( (int) calInterest ));

        if(dataItem.getFees().size() > 0){

            fees.setText(CommonUtils.setRupiahCurrency(dataItem.getFees().get(0).getAmount()));

            for(FeesItem fee:dataItem.getFees()){

                admin = fee.getAmount();
            }

            calculateTotalBiaya = ((int)calInterest * dataItem.getInstallment()) + (admin * dataItem.getInstallment()) + dataItem.getLoanAmount();
        }else {

            calculateTotalBiaya = ((int)calInterest * dataItem.getInstallment())  + dataItem.getLoanAmount();

        }

        angsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(dataItem.getLayawayPlan())));

        totalBiaya.setText(CommonUtils.setRupiahCurrency(calculateTotalBiaya));


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
