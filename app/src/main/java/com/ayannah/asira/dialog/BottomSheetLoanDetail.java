package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.FeesItem;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.util.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomSheetLoanDetail extends BottomSheetDialogFragment {

    private static final int CREATED = 1;
    private static final int DISBURSE = 2;
    private static final int APPROVE = 3;


    private Unbinder mUnbinder;

    private DataItem item;

    private BottomSheetLoanDetailListener listener;

    public void setDataLoan(DataItem result){
        item = result;
    }

    public void setOnClickListener(BottomSheetLoanDetailListener listener){
        this.listener = listener;

    }

    @BindView(R.id.idPinjaman) TextView idpinjaman;
    @BindView(R.id.nama) TextView nama;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.jenis) TextView jenis;
    @BindView(R.id.produk) TextView produk;
    @BindView(R.id.pinjamanPokok) TextView pinjamanPokok;
    @BindView(R.id.tenor) TextView tenor;
    @BindView(R.id.total) TextView total;
    @BindView(R.id.angsuran) TextView angsuran;
    @BindView(R.id.imbalHasil) TextView imbalHasil;
    @BindView(R.id.imbal) TextView imbal;
    @BindView(R.id.tvAdminFee) TextView tvAdminFee;
    @BindView(R.id.admin) TextView admin;
    @BindView(R.id.tvConvenience) TextView tvConvenience;
    @BindView(R.id.convenience) TextView convenience;
    @BindView(R.id.tujuan) TextView tujuan;
    @BindView(R.id.detail) TextView detail;
    @BindView(R.id.tanggalPengajuan) TextView tanggalPengajuan;
    @BindView(R.id.tanggalPencairan) TextView tanggalPencairan;
    @BindView(R.id.tanggalApproval) TextView tanggalApproval;
    @BindView(R.id.lltTanggalApproval) LinearLayout lltTanggalApproval;
    @BindView(R.id.lltTanggalPencairan) LinearLayout lltTanggalPencairan;
    @BindView(R.id.lltRejactReason) LinearLayout lltRejactReason;
    @BindView(R.id.rejectReason) TextView rejectReason;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_loan_detail, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        idpinjaman.setText(String.valueOf(item.getId()));

        nama.setText(String.format("%s (%s)", item.getBorrowerInfo().getFullname(), item.getBorrowerInfo().getNickname()));

        if(item.getStatus().equals("approved")){
            if (item.getDisburseStatus().equals("confirmed")) {
                status.setText("Sudah DIcairkan");
            } else {
                status.setText("Diterima");
            }
            lltTanggalApproval.setVisibility(View.VISIBLE);
            lltTanggalPencairan.setVisibility(View.VISIBLE);
        }else if(item.getStatus().equals("processing")){
            status.setText("Dalam proses");
        }else {
            status.setText("Ditolak");
            lltTanggalApproval.setVisibility(View.VISIBLE);
            lltRejactReason.setVisibility(View.VISIBLE);
        }


        jenis.setText(item.getServiceName());

        produk.setText(item.getProductName());

        pinjamanPokok.setText(CommonUtils.setRupiahCurrency(item.getLoanAmount()));

        tenor.setText(String.valueOf(item.getInstallment()));

        total.setText(CommonUtils.setRupiahCurrency(item.getTotalLoan()));

        angsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(item.getLayawayPlan())));

        imbalHasil.setText("Imbal Hasil "+ (int)item.getInterest() +"%");

        double xyz = (item.getInterest() * (double)item.getLoanAmount())/100;
        double loanAmount = Double.parseDouble(String.valueOf(item.getLoanAmount()));
        imbal.setText(CommonUtils.setRupiahCurrency((int) Math.floor(xyz)));

        if(item.getFees().size() > 0) {
            for (FeesItem data : item.getFees()) {

                if (data.getDescription().contains("Admin Fee")){

                    double x = Double.parseDouble(data.getAmount());
                    double percentageAdmin = ( x / loanAmount) * 100;

                    tvAdminFee.setText("Admin Fee "+checkDecimal(percentageAdmin) +"%");
                    admin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(x)));

                }else {

                    double y = Double.parseDouble(data.getAmount());
                    double percentageConvenience = (y/loanAmount) * 100;

                    tvConvenience.setText("Convenience Fee "+checkDecimal(percentageConvenience) + "%");
                    convenience.setText(CommonUtils.setRupiahCurrency((int) Math.floor(y)));

                }

            }
        }

        tujuan.setText(item.getLoanIntention());

        detail.setText(item.getIntentionDetails());

        tanggalPengajuan.setText(formatDate(item.getCreatedTime(), CREATED));

        tanggalPencairan.setText(formatDate(item.getDisburseDate(), DISBURSE));

        tanggalApproval.setText(formatDate(item.getUpdatedTime(), APPROVE));

        rejectReason.setText(item.getRejectReason());

        return view;
    }

    private String checkDecimal(double percentageAdmin) {

        String convertToString = String.valueOf(percentageAdmin);

        String pattern_no_decimal = "([0-99][.][0+])";

        return convertToString.matches(pattern_no_decimal) ? convertToString.substring(0,1) : String.valueOf(percentageAdmin);

    }

    private String formatDate(String datetime, int purposes){

        String result = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());
        SimpleDateFormat sdfUsed = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        Date date;

        try {

            switch (purposes){

                case CREATED:
                case APPROVE:

                    date = sdf.parse(datetime);

                    result = sdfUsed.format(date);

                    break;
                case DISBURSE:

                    date = sdf2.parse(datetime);

                    result = sdfUsed.format(date);

                    break;

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result == null ? "Format date salah" : result;
    }

    @OnClick(R.id.close)
    void closeDialog(){
        listener.close();
    }

    public interface BottomSheetLoanDetailListener{

        void close();

    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
