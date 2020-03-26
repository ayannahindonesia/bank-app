package com.ayannah.asira.screen.detailangsuran.detail_pembayaran;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.util.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailPembayaranFragment extends BaseFragment {

    @BindView(R.id.amount) TextView tvAmount;
    @BindView(R.id.status) TextView tvStatus;
    @BindView(R.id.period) TextView tvPeriod;
    @BindView(R.id.dueDate) TextView tvDueDate;
    @BindView(R.id.cicilan) TextView tvCicilan;
    @BindView(R.id.penalty) TextView tvPenalty;
    @BindView(R.id.amountPay) TextView tvAmountPay;
    @BindView(R.id.datetimePay) TextView tvDatetimePay;
    @BindView(R.id.countPenalty) TextView tvCountPenalty;
    @BindView(R.id.cacatan) TextView tvCacatan;

    private InstallmentDetails installment;

    @Inject
    public DetailPembayaranFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_detail_pembayaran;
    }

    @Override
    protected void initView(Bundle state) {

        Bundle bundle = getArguments();

        installment = bundle.getParcelable("details");

        setUpValue(installment);
    }

    private void setUpValue(InstallmentDetails param) {

        tvAmount.setText(CommonUtils.setRupiahCurrency(param.getLoanPayment()));

        if(param.isPaidStatus()){
            tvStatus.setBackgroundResource(R.drawable.bg_status_approve);
            tvStatus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            tvStatus.setText("Telah dibayar");
        }else {
            tvStatus.setBackgroundResource(R.drawable.bg_status_normal);
            tvStatus.setTextColor(Color.BLACK);
            tvStatus.setText("Belum dibayar");
        }

        tvPeriod.setText(String.format("%s", param.getPeriod() < 10 ? "0"+param.getPeriod() : param.getPeriod()));

        tvDueDate.setText(CommonUtils.formatDateBirth(param.getDueDate()));

        tvCicilan.setText(CommonUtils.setRupiahCurrency(param.getLoanPayment()));

        tvPenalty.setText(CommonUtils.setRupiahCurrency(param.getPenalty()));

        tvAmountPay.setText(CommonUtils.setRupiahCurrency(param.getPaidAmount()));

        if(param.getPaidDate() != null) {
            tvDatetimePay.setText(CommonUtils.formatDateBirth(param.getPaidDate()));
        }

        tvCountPenalty.setText(String.valueOf(param.getPenalty()));

        tvCacatan.setText(param.getNote());

    }
}
