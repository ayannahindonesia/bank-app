package com.ayannah.asira.screen.loan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.PlafondEditText;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.screen.summary.SummaryTransactionActivity;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.NumberSeparatorTextWatcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class LoanFragment extends BaseFragment implements LoanContract.View {

    @Inject
    LoanContract.Presenter mPresenter;

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

    @BindView(R.id.jumlahPencairan)
    TextView jumlahPencairan;

    @BindView(R.id.biayaAdmin)
    TextView biayaAdmin;

    @BindView(R.id.spAlasanPinjam)
    Spinner spAlasanPinjam;

    @BindView(R.id.lyAlasanLain)
    LinearLayout lyAlasanLain;

    @BindView(R.id.plafonMinMax)
    TextView plafonMinMax;

    @BindView(R.id.etAlasan)
    EditText etAlasan;

    @BindView(R.id.etTujuan)
    EditText etTujuan;

    @BindView(R.id.spProducts)
    Spinner spProducts;

    @BindView(R.id.plafondCustom)
    PlafondEditText plafondCustom;

    private AlertDialog dialog;

    private int selectedProduct = 0;

    //calculation purposes
    private int administration = 0;
    private int loanAmount = 0;
    private double interest = 0;
    private int installmentTenor = 0;
    private double angsurnaPerbulan = 0;
    private int productID = 0;
    private double countPencairan = 0.0;
    private int totalBunga = 0;

    //define min and max loan var globally based on selected product loan
    private int minPlafond = 0;
    private int maxPlafond = 0;

    //define min and max tenor var globally based on selected product loan
    private int minTenor = 0;
    private int maxTenor = 0;

    //define adminfee and convfee setup
    private static final String POTONG_PLAFON = "potong_plafon";
    private static final String BEBAN_PLAFON = "beban_plafon";
    private String adminSetup;
    private String convSetup;

    private List<String> productName;
    private ServiceProducts mServiceProducts;
    private NumberSeparatorTextWatcher plafonNumberSeparator;

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

        mPresenter.getReasonLoan();

        mPresenter.getRulesFormula();

    }

    @Override
    protected void initView(Bundle state) {

        //memberikan number currenxt saat input angka plafon pinjaman
        plafonNumberSeparator = new NumberSeparatorTextWatcher(plafondCustom);
        plafondCustom.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                plafondCustom.addTextChangedListener(plafonNumberSeparator);
            }
        });
        plafondCustom.setFocusable(true);

        installment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (!plafondCustom.getText().toString().trim().isEmpty()) {
                    installmentTenor = minTenor + (progress * 6);

                    //calculate bunga
                    totalBunga = (int) (loanAmount * interest) / 100;

                    //calculate angsuran perbulan
                    angsurnaPerbulan = calculateAngsuranPerBulan(convSetup);

//                //calculate jumlapencairan
//                countPencairan = calculatePotongPlafond(loanAmount)/installmentTenor;

                    tvInstallment.setText(String.format("%s bulan", installmentTenor));
                    biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
                    tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(totalBunga)));
                    tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));
                    jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) Math.floor(countPencairan)));

                } else {
//                    Toast.makeText(parentActivity(), "Masukan Jumlah Pinjaman Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    installment.setProgress(0);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @OnClick(R.id.buttonPinjam)
    void onClickPinjam(){
        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;

        //user should choose product or product must be not null
        if (productName == null) {
            Toast.makeText(parentActivity(), "Produk Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
            return;
        }

        //user should input plafond
        if(loanAmount == 0 || loanAmount < minPlafond || loanAmount > maxPlafond){

            CalculateData(selectedProduct, mServiceProducts);

        }

        Intent intent = new Intent(parentActivity(), SummaryTransactionActivity.class);

        intent.putExtra(SummaryTransactionActivity.PINJAMAN, loanAmount);
        intent.putExtra(SummaryTransactionActivity.TENOR, installmentTenor);
        intent.putExtra(SummaryTransactionActivity.ANGSURAN_BULAN, angsurnaPerbulan);
        intent.putExtra(SummaryTransactionActivity.PRODUK, spProducts.getSelectedItem().toString());
        intent.putExtra(SummaryTransactionActivity.PRODUCTID, productID);
        intent.putExtra(SummaryTransactionActivity.ADMIN, administration);
        intent.putExtra(SummaryTransactionActivity.INTEREST, totalBunga);
        intent.putExtra(SummaryTransactionActivity.PENCAIRAN, countPencairan);

        if(spAlasanPinjam.getSelectedItem().toString().equals("Lain-lain")){

            if(etAlasan.getText().toString().trim().isEmpty()){

                Toast.makeText(parentActivity(), "Mohon diisi alasannya", Toast.LENGTH_SHORT).show();
                etAlasan.requestFocus();
                return;
            }

            intent.putExtra(SummaryTransactionActivity.ALASAN, etAlasan.getText().toString());


        } else {

            intent.putExtra(SummaryTransactionActivity.ALASAN, spAlasanPinjam.getSelectedItem().toString());

        }

        intent.putExtra(SummaryTransactionActivity.TUJUAN, etTujuan.getText().toString());
        intent.putExtra(SummaryTransactionActivity.LAYANAN, bundle.getInt("idService"));
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

            //set global variable to get all value from service product
            mServiceProducts = serviceProducts;

            productName = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                if (serviceProducts.getProducts().get(i).getStatus().equals("active")) {
                    productName.add(serviceProducts.getProducts().get(i).getName());
                }
            }

            ArrayAdapter<String> mAdapterProducts = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, productName);
            spProducts.setAdapter(mAdapterProducts);

            spProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //set global variable to get value from selected product which user selected
                    selectedProduct = position;

                            //set default loan
                    installment.setProgress(0);
                    administration = 0;

                    //set default loan
                    loanAmount = 0;
                    plafondCustom.setText("");
                    biayaAdmin.setText("-");
                    tvBunga.setText("-");

                    //set default jumlah angsuran
                    angsurnaPerbulan = 0;
                    tvAngsuran.setText("-");

                    //set default jumlah pencairan
                    countPencairan = 0;
                    jumlahPencairan.setText("-");

                    //set min and max plafond based on selected product
                    minPlafond = serviceProducts.getProducts().get(position).getMinLoan();
                    maxPlafond = serviceProducts.getProducts().get(position).getMaxLoan();

                    //set min and max tenor based on selected product
                    minTenor = serviceProducts.getProducts().get(position).getMinTimespan();
                    maxTenor = serviceProducts.getProducts().get(position).getMaxTimespan();
                    int maxTenorSeekbar = ((maxTenor-minTenor)/6);
                    installment.setMax(maxTenorSeekbar);

                    installmentTenor = minTenor;
                    tvInstallment.setText(String.format("%s bulan", installmentTenor));

                    //set plafond sesuai dengan product yang dipilih
                    plafonMinMax.setText(String.format("Min %s - Max %s", CommonUtils.setRupiahCurrency(serviceProducts.getProducts().get(position).getMinLoan()),
                            CommonUtils.setRupiahCurrency(serviceProducts.getProducts().get(position).getMaxLoan())));

                    //set listener if user click back on the phone after type number of plafond
                    plafondCustom.setOnHideSoftKeyboardAction((keyCode, event) -> {

                        if (keyCode == KeyEvent.KEYCODE_BACK){

                            CalculateData(position, serviceProducts);

                        }
                    });

                    //set listener if user click OK after type number of plafind
                    plafondCustom.setOnEditorActionListener((v, keyCode, event) -> {

                        if(keyCode == EditorInfo.IME_ACTION_DONE){

                            CalculateData(position, serviceProducts);

                        }
                        return false;
                    });

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        } else {

            Toast.makeText(parentActivity(), "Produk Kosong", Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }

    private void CalculateData(int position, ServiceProducts serviceProducts) {

        if(!plafondCustom.getText().toString().trim().isEmpty()){

            installment.setProgress(0);

            //get value from edittext to set plafond
            int nominal = Integer.parseInt(plafondCustom.getText().toString().replaceAll(",", ""));
            int nominalRound = roundingValue(nominal);
            administration = calculateAdministration(nominalRound, serviceProducts.getProducts().get(position).getFees().get(0).getAmount(), serviceProducts.getProducts().get(position).getAsnFee());
            productID = mServiceProducts.getProducts().get(position).getId();

            if(nominalRound < serviceProducts.getProducts().get(position).getMinLoan()){

                // jumlah pinjaman lebih kecil dari batas minimum
                Toast.makeText(parentActivity(), "Jumlah pinjmana lebih kecil dari batas minimum", Toast.LENGTH_SHORT).show();


            }else if(nominalRound > serviceProducts.getProducts().get(position).getMaxLoan()){

                // Jumlaj pinhaman lebih besar dari batas maksimum
                Toast.makeText(parentActivity(), "Jumlah pinjmana lebih besar dari batas maximum", Toast.LENGTH_SHORT).show();

            }else {

                //acceptable
                Toast.makeText(parentActivity(), "Diterima", Toast.LENGTH_SHORT).show();

                //set rincian harga
                String value = plafondCustom.getText().toString().replaceAll(",", "");
                loanAmount = Integer.parseInt(value);

                //base on seekbar installment
//                installmentTenor = (installment.getVerticalScrollbarPosition()+1) * 6;
                installmentTenor = minTenor;

                //calculate bunga
                interest = serviceProducts.getProducts().get(position).getInterest();
                totalBunga = (int) (loanAmount * interest) / 100;

                //calculate angsuran perbulan
                angsurnaPerbulan = calculateAngsuranPerBulan(convSetup);

//                //calculate jumlapencairan
//                int asnfee = 0;
//                if(serviceProducts.getProducts().get(position).getAsnFee().contains("%")){
//
//                    asnfee = Integer.parseInt(serviceProducts.getProducts().get(position).getAsnFee().replaceAll("%", ""));
//
//                    countPencairan = calculateJumlahPencairanInPercent(loanAmount, asnfee)/installmentTenor;
//
//                }else {
//
//                    asnfee = Integer.parseInt(serviceProducts.getProducts().get(position).getAsnFee());
//
//                    countPencairan = (calculatePotongPlafond(loanAmount) - asnfee) /installmentTenor;
//                }

                if (convSetup.equals(POTONG_PLAFON)) {
                    countPencairan = loanAmount - administration;
                } else {
                    countPencairan = loanAmount;
                }

                tvInstallment.setText(String.format("%s bulan", installmentTenor));
                biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
                tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(totalBunga)));
                tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));
                jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) Math.floor(countPencairan)));
            }

        }else {

            Toast.makeText(parentActivity(), "Mohon isi jumlah pinjaman", Toast.LENGTH_SHORT).show();
        }
    }

    private int roundingValue(int plafond) {
        int plafondFix = plafond;
        int xModular = plafondFix%100000;

        if (plafondFix%100000 != 0) {
            plafondFix = plafondFix - xModular;
            plafondCustom.setText(String.valueOf(plafondFix));

            return plafondFix;
        }

        return plafondFix;
    }

    @Override
    public void showReason(List<ReasonLoan.Data> data) {

        List<String> chooser = new ArrayList<>();
        for(ReasonLoan.Data x:data){
            if(x.getStatus().equals("active")) {
                chooser.add(x.getName());
            }
        }
        chooser.add("Lain-lain");

        ArrayAdapter<String> mAdapterAlasan = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, chooser);
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
    }

    @Override
    public void setupFormulaFee(String adminsetup, String convsetup) {

        adminSetup = adminsetup;

        convSetup = convsetup;

    }

    private double calculatePotongPlafond(double plafond){

        return plafond - administration;
    }

    //hitung jumlah pencairan dengan membebankan keccicilan
    private double calculateJumlahPencairanInPercent(double plafond, int asnFee){

        double countAsnFee = plafond * asnFee / 100;

        return plafond - (administration + countAsnFee);
    }

    //hitung biaya adminsitrasi
    //tes bkin formulasi administrasi
    private int calculateAdministration(int plafon, String adminFee, String asnFee){
        int calAdminFee;
        int calAsnFee;

        if (adminFee.contains("%")) {
            double adminFeeX = Double.parseDouble(adminFee.replace("%", ""));
            calAdminFee = (int) (plafon * adminFeeX / 100);
        } else {
            calAdminFee = Integer.parseInt(adminFee);
        }

        if (asnFee.contains("%")) {
            double asnFeeX = Double.parseDouble(asnFee.replace("%", ""));
            calAsnFee = (int) (plafon * asnFeeX / 100);
        } else {
            calAsnFee = Integer.parseInt(asnFee);
        }

//        int admin = plafon * calAdminFee / 100;
//
//        int asnfees = 0;
//
//        if (asnFee > 100) {
//
//            asnfees = asnFee;
//
//        } else {
//
//            asnfees = plafon * asnFee / 100;
//        }

        return calAdminFee + calAsnFee;

    }

    @OnClick(R.id.plafondCustom)
    void onClickPlafondEdittext(){

        plafondCustom.setText("");

    }

    private double calculateAngsuranPerBulan(String jenisPotong) {

        if (jenisPotong.equals(POTONG_PLAFON)) {

            return (loanAmount + totalBunga) / installmentTenor;

        } else {

            return (loanAmount + totalBunga + administration) / installmentTenor;
        }

    }
}
