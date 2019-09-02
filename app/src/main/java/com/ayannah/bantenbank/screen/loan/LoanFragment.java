package com.ayannah.bantenbank.screen.loan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.BankDetail;
import com.ayannah.bantenbank.data.model.FeesItem;
import com.ayannah.bantenbank.data.model.Products;
import com.ayannah.bantenbank.data.model.ServiceProducts;
import com.ayannah.bantenbank.screen.summary.SummaryTransactionActivity;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.util.CommonUtils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class LoanFragment extends BaseFragment implements LoanContract.View {

    @Inject
    LoanContract.Presenter mPresenter;

    @BindView(R.id.seekbarJumlahPinjaman)
    SeekBar sbJumlahPinjaman;

    @BindView(R.id.seekbarTenorCicilan)
    SeekBar installment;

    @BindView(R.id.nominalPinjaman)
    TextView amountLoan;

    @BindView(R.id.tenorCicilan)
    TextView tvInstallment;

    @BindView(R.id.amountBunga)
    TextView tvBunga;

    @BindView(R.id.angsuranPerbulan)
    TextView tvAngsuran;

    @BindView(R.id.saldoPokokPinjaman)
    TextView saldoPokokPinjaman;

    @BindView(R.id.biayaAdmin)
    TextView biayaAdmin;

    @BindView(R.id.spAlasanPinjam)
    Spinner spAlasanPinjam;

    @BindView(R.id.lyAlasanLain)
    LinearLayout lyAlasanLain;

    @BindView(R.id.etAlasan)
    EditText etAlasan;

    @BindView(R.id.etTujuan)
    EditText etTujuan;

    @BindView(R.id.spProducts)
    Spinner spProducts;

    private AlertDialog dialog;

    double administration = 1000;

    int[] loanRepo = {5000000, 10000000, 15000000, 20000000, 25000000, 30000000, 35000000, 40000000, 45000000, 50000000};
    double loanAmount = 0;
    int interest = 0;

    int installmentTenor = 0;
    double angsurnaPerbulan = 0;

    private List<String> productName;
    private ServiceProducts mServiceProducts;

//    double saldoPinjaman = 0;
    String[] alasan = {"Pendidikan", "Pembelian rumah", "Rumah tangga", "Liburan", "Kendaraan", "Umroh", "Lain-lain"};

    @Inject
    public LoanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_loan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();
        mPresenter.getProducts();

    }

    @Override
    protected void initView(Bundle state) {

        calculateDefaultValue();

        ArrayAdapter<String> mAdapterAlasan = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, alasan);
        spAlasanPinjam.setAdapter(mAdapterAlasan);
        spAlasanPinjam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().equals("Lain-lain")){

                    lyAlasanLain.setVisibility(View.VISIBLE);
                }else {
                    lyAlasanLain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sbJumlahPinjaman.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //jumlah pinjaman default 5 jt
                loanAmount = Double.parseDouble(String.valueOf(loanRepo[progress]));

                //convert jumlah pinjaman ke format currency menggunakan rupiah
                amountLoan.setText( CommonUtils.setRupiahCurrency(loanRepo[progress]) );

                //tenor peminjaman secara default dari 6 bulan.
                if(installment.getProgress() == 0) {

                    installmentTenor = (installment.getProgress() + 1) * 6;
                }else {

                    installmentTenor = (installment.getProgress() + 1) * 6;
                }

                //calculate bunga
                double bunga =  (loanAmount * interest) / 100;

                //calculate angsuran perbulan
                angsurnaPerbulan = (loanAmount + bunga + administration) / installmentTenor;

                tvInstallment.setText(String.format("%s bulan", installmentTenor));
                biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
                tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(bunga)));
                tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        installment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                installmentTenor = (progress+1) * 6;

                //calculate bunga
                double bunga = (loanAmount * interest) / 100;

                //calculate angsuran perbulan
                angsurnaPerbulan = (loanAmount + bunga + administration) / installmentTenor;

                tvInstallment.setText(String.format("%s bulan", installmentTenor));
                biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
                tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(bunga)));
                tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void calculateDefaultValue() {

        //based on seekbar jumlah pinjaman
        loanAmount = Double.parseDouble(String.valueOf(loanRepo[sbJumlahPinjaman.getVerticalScrollbarPosition()]));
        amountLoan.setText( CommonUtils.setRupiahCurrency(loanRepo[sbJumlahPinjaman.getVerticalScrollbarPosition()]) );

        //base on seekbar installment
        installmentTenor = (installment.getVerticalScrollbarPosition()+1) * 6;

        //calculate bunga
        double bunga = (loanAmount * interest) / 100;

        //calculate angsuran perbulan
        angsurnaPerbulan = (loanAmount + bunga + administration) / installmentTenor;

        tvInstallment.setText(String.format("%s bulan", installmentTenor));
        biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
        tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(bunga)));
        tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));

    }

    @OnClick(R.id.buttonPinjam)
    void onClickPinjam(){

        if (productName == null) {
            Toast.makeText(parentActivity(), "Produk Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(parentActivity(), SummaryTransactionActivity.class);

        intent.putExtra(SummaryTransactionActivity.PINJAMAN, loanAmount);
        intent.putExtra(SummaryTransactionActivity.TENOR, installmentTenor);
        intent.putExtra(SummaryTransactionActivity.ANGSURAN_BULAN, angsurnaPerbulan);
        intent.putExtra(SummaryTransactionActivity.PRODUK, spProducts.getSelectedItem().toString());

        if(spAlasanPinjam.getSelectedItem().toString().equals("Lain-lain")){

            if(etAlasan.getText().toString().trim().isEmpty()){

                Toast.makeText(parentActivity(), "Mohon diisi alasannya", Toast.LENGTH_SHORT).show();
                etAlasan.requestFocus();
                return;
            }

            intent.putExtra(SummaryTransactionActivity.ALASAN, etAlasan.getText().toString());


        }else {

            intent.putExtra(SummaryTransactionActivity.ALASAN, spAlasanPinjam.getSelectedItem().toString());

        }

        intent.putExtra(SummaryTransactionActivity.TUJUAN, etTujuan.getText().toString());
        startActivity(intent);

    }

    @Override
    public void showErrorMessage(String message) {
        dialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successGetProducts(ServiceProducts serviceProducts) {
        int size = serviceProducts.getProducts().size();
        if (size > 0) {
            mServiceProducts = serviceProducts;
            productName = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                if (serviceProducts.getProducts().get(i).getStatus().equals("active")) {
                    productName.add(serviceProducts.getProducts().get(i).getName());
                }
            }

            ArrayAdapter<String> mAdapterProducts = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, productName);
            spProducts.setAdapter(mAdapterProducts);
        } else {
            Toast.makeText(parentActivity(), "Produk Kosong", Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }

    @OnItemSelected(R.id.spProducts)
    void ClickProduct(Spinner spinner, int position) {
        administration = Integer.parseInt(mServiceProducts.getProducts().get(position).getFees().getAmount());
        interest = mServiceProducts.getProducts().get(position).getInterest();
        sbJumlahPinjaman.setProgress(0);
        installment.setProgress(0);

        calculateDefaultValue();
    }
}
