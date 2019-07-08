package com.ayannah.bantenbank.screen.summary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;
import com.ayannah.bantenbank.util.CommonUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class SummaryTransactionFragment extends BaseFragment implements SummaryTransactionContract.View {

    @Inject
    SummaryTransactionContract.Presenter mPresenter;

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

//    @BindView(R.id.saldoPokokPinjaman)
//    TextView tvSaldoPokokPinjaman;

    @BindView(R.id.alasan)
    TextView tvAlasan;

    @BindView(R.id.tujuanPinjam)
    TextView tvTujuanPinjam;

    //value purposes
    @Inject
    @Named("pinjaman")
    double pinjaman;

    @Inject
    @Named("tenor")
    int tenor;

    @Inject
    @Named("angsuran_bulan")
    double angsuranBulan;

//    @Inject
//    @Named("saldo")
//    double saldoPinjaman;

    @Inject
    @Named("alasan")
    String alasan;

    @Inject
    @Named("tujuan")
    String tujuan;

    @Inject
    public SummaryTransactionFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_summary_transaction;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void initView(Bundle state) {

        setValueIntent();

        tvPinjaman.setText(CommonUtils.setRupiahCurrency( (int) pinjaman) );

        tvTenor.setText(String.format("%s bulan)", tenor));

        double calculateBunga = ((pinjaman * 13)/100 ) / (tenor * 12);
        tvBunga.setText(CommonUtils.setRupiahCurrency((int) calculateBunga));

        double calBiayaAdmin = (pinjaman * 1.5)/100;
        tvBiayaAdmin.setText(CommonUtils.setRupiahCurrency((int) calBiayaAdmin));

        double calAngsuran = (pinjaman / (tenor * 12)) + calculateBunga;
        tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(calAngsuran)));

//        double saldoPinjaman = pinjaman - (pinjaman / (tenor * 12));
//        tvSaldoPokokPinjaman.setText(CommonUtils.setRupiahCurrency((int) Math.floor(saldoPinjaman)));

        tvAlasan.setText(alasan);

        tvTujuanPinjam.setText(tujuan);

    }

    private void setValueIntent() {
//        pinjaman = parentActivity().getIntent().getDoubleExtra(SummaryTransactionActivity.PINJAMAN, 0);
//
//        tenor = parentActivity().getIntent().getIntExtra(SummaryTransactionActivity.TENOR, 0);
//
//        angsuranBulan = parentActivity().getIntent().getDoubleExtra(SummaryTransactionActivity.ANGSURAN_BULAN, 0);
//
//        saldoPinjaman = parentActivity().getIntent().getDoubleExtra(SummaryTransactionActivity.SALDO_PINJAMAN, 0);
//
//        alasan = parentActivity().getIntent().getStringExtra(SummaryTransactionActivity.ALASAN);
//
//        tujuan = parentActivity().getIntent().getStringExtra(SummaryTransactionActivity.TUJUAN);

    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
        intent.putExtra("purpose", "pinjaman");
        startActivity(intent);

    }

    @Override
    public void showErrorMessages(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successLoanApplication(String id_loan) {

    }

    @Override
    public void successGetOtp(String loanOTP) {

    }


}
