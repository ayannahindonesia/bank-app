package com.ayannah.asira.screen.detailloan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.data.local.ServiceProductLocal;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.Loans.FeesItem;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.ayannah.asira.util.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class DetailTransaksiActivity extends DaggerAppCompatActivity implements DetailTransaksiContract.View  {

    public static final String ID_LOAN = "idloan";

    private final static String STATUS_PROCESSING = "processing";
    private final static String STATUS_APPROVED = "approved";
    private final static String STATUS_REJECTED = "rejected";

    @Inject
    DetailTransaksiContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.verfiedLoan)
    Button btn_verfiedLoan;

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

    @BindView(R.id.produk)
    TextView produk;

    private Unbinder mUnbinder;

    int admin = 0;
    int calculateTotalBiaya = 0;
    int idLoan = 0;
    private AlertDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();

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
        dismissDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public void loadAllInformation(DataItem dataItem) {
        ServiceProductLocal serviceProductLocal = new ServiceProductLocal(getBaseContext());
        try {
            JSONArray jsonArray1 = new JSONArray(serviceProductLocal.getServiceProducts());
            for (int j = 0; j < jsonArray1.length(); j++) {
                JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonArray1.get(j)));
                if (dataItem.getProduct().equals(jsonObject2.get("id").toString())) {
//                    productNya = jsonObject2.get("name").toString();
                    produk.setText(jsonObject2.get("name").toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        idLoan = dataItem.getId();

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

        switch (dataItem.getStatus().toLowerCase()){
            case STATUS_APPROVED:
                status.setBackgroundResource(R.drawable.badge_diterima);
                status.setText(getResources().getString(R.string.accept));
                break;
            case STATUS_PROCESSING:
                status.setBackgroundResource(R.drawable.badge_tidak_lengkap);
                status.setText(getResources().getString(R.string.processing));
                break;
            case STATUS_REJECTED:
                status.setBackgroundResource(R.drawable.badge_ditolak);
                status.setText(getResources().getString(R.string.reject));
                break;
        }

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

//        produk.setText();

        if(!dataItem.isOtpVerified()){

            btn_verfiedLoan.setVisibility(View.VISIBLE);
        }

        dismissDialog();

    }

    @Override
    public void showResultLoanOnProcess(boolean isExist) {

        /*
        Jika ada loan yang statusnya masih processing, maka tidak bisa mengajukan ulang loan yang telah dipilih.
        Jika tidak ada, maka sebaliknya
         */

        if(isExist){

            //true
            BottomSheetDialogGlobal dialog = new BottomSheetDialogGlobal().show(getSupportFragmentManager(),
                    BottomSheetDialogGlobal.RESEND_LOAN_FORBIDDEN,
                    "Pengajuan Ulang Dibatalkan",
                    "Kamu belum bisa mengajukan ulang peminjaman ini sampai pengajuan sebelumnya telah selesai dari proses",
                    R.drawable.img_processing);
            dialog.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
                @Override
                public void onClickButtonDismiss() {
                    dialog.dismiss();
                }

                @Override
                public void onClickButtonYes() {
                    //this action no need for this features
                }

                @Override
                public void closeApps() {
                    //this action no need for this features
                }
            });

        }else {

            //false
            Intent submit = new Intent(this, VerificationOTPActivity.class);
            submit.putExtra(VerificationOTPActivity.PURPOSES, "resubmit_loan");
            submit.putExtra(VerificationOTPActivity.IDLOAN, idLoan);
            submit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(submit);
            finish();
        }
    }

    @OnClick(R.id.verfiedLoan)
    void onClickSubmitLoan(){

        Intent submit = new Intent(this, VerificationOTPActivity.class);
        submit.putExtra(VerificationOTPActivity.PURPOSES, "resubmit_loan");
        submit.putExtra(VerificationOTPActivity.IDLOAN, idLoan);
        submit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(submit);
        finish();

//        mPresenter.checkLoanOnProcess();

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
