package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @BindView(R.id.admin) TextView admin;
    @BindView(R.id.convenience) TextView convenience;
    @BindView(R.id.tujuan) TextView tujuan;
    @BindView(R.id.detail) TextView detail;
    @BindView(R.id.tanggalPengajuan) TextView tanggalPengajuan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_loan_detail, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        idpinjaman.setText(String.valueOf(item.getId()));

        nama.setText(item.getBorrowerInfo().getFullname());

        if(item.getStatus().equals("approved")){
            status.setText("Diterima");
        }else if(item.getStatus().equals("processing")){
            status.setText("Dalam proses");
        }else {
            status.setText("Ditolak");
        }


        jenis.setText(item.getServiceName());

        produk.setText(item.getProductName());

        pinjamanPokok.setText(CommonUtils.setRupiahCurrency(item.getLoanAmount()));

        tenor.setText(String.valueOf(item.getInstallment()));

        total.setText(CommonUtils.setRupiahCurrency(item.getTotalLoan()));

        angsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(item.getLayawayPlan())));

        imbalHasil.setText("Imbal hasil "+ (int)item.getInterest() +"%");

        double xyz = (item.getInterest() * (double)item.getLoanAmount())/100;
        imbal.setText(CommonUtils.setRupiahCurrency((int) Math.floor(xyz)));

        if(item.getFees().size() > 0) {
            for (FeesItem data : item.getFees()) {

                if (data.getDescription().contains("Admin Fee")){

                    double x = Double.parseDouble(data.getAmount());
                    admin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(x)));

                }else {

                    double y = Double.parseDouble(data.getAmount());
                    convenience.setText(CommonUtils.setRupiahCurrency((int) Math.floor(y)));

                }

            }
        }

        tujuan.setText(item.getLoanIntention());

        detail.setText(item.getIntentionDetails());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat sdfUsed = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date getDate = new Date();
        try {
            getDate = sdf.parse(item.getCreatedTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tanggalPengajuan.setText(sdfUsed.format(getDate));

        return view;
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
