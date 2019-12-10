package com.ayannah.asira.screen.agent.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.PlafondEditText;
import com.ayannah.asira.data.model.FeesItem;
import com.ayannah.asira.data.model.Products;
import com.ayannah.asira.data.model.ProductsAgent;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.data.model.ServiceProductsAgent;
import com.ayannah.asira.screen.summary.SummaryTransactionActivity;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.NumberSeparatorTextWatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoanAgentFragment extends BaseFragment implements LoanAgentContract.View {

    @Inject
    LoanAgentContract.Presenter mPresenter;

    @BindView(R.id.seekbarTenorCicilan)
    SeekBar installment;

    @BindView(R.id.lyDataProdukEmpty)
    RelativeLayout lyDataProdukEmpty;

    @BindView(R.id.lyDataAlasanEmpty)
    RelativeLayout lyDataAlasanEmpty;

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

    @Inject
    String idService;

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
    private ArrayList<ProductsAgent> mServiceProducts = new ArrayList<>();
    private NumberSeparatorTextWatcher plafonNumberSeparator;

    @Inject
    public LoanAgentFragment(){}

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
        mPresenter.getProducts(idService);

        mPresenter.getLoanIntention();

        mPresenter.getRulesFormula(getActivity().getIntent().getStringExtra(LoanAgentActivity.IDBANK));

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

                    tvInstallment.setText(String.format("%s bulan", installmentTenor));
                    biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
                    tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(totalBunga)));
                    tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));
                    jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) Math.floor(countPencairan)));

                } else {

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

    @Override
    public void showErrorMessage(String message) {
        dialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successGetProducts(ServiceProductsAgent serviceProducts) {
        int size = serviceProducts.getProducts().size();

        if (size > 0) {

            spProducts.setVisibility(View.VISIBLE);
            lyDataProdukEmpty.setVisibility(View.GONE);

            productName = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                if (serviceProducts.getProducts().get(i).getStatus().equals("active")) {
                    productName.add(serviceProducts.getProducts().get(i).getName());
                    //set global variable to get all value from service product
                    mServiceProducts.add(serviceProducts.getProducts().get(i));
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
                    minPlafond = mServiceProducts.get(position).getMinLoan();
                    maxPlafond = mServiceProducts.get(position).getMaxLoan();

                    //set min and max tenor based on selected product
                    minTenor = mServiceProducts.get(position).getMinTimespan();
                    maxTenor = mServiceProducts.get(position).getMaxTimespan();
                    int maxTenorSeekbar = ((maxTenor-minTenor)/6);
                    installment.setMax(maxTenorSeekbar);

                    installmentTenor = minTenor;
                    tvInstallment.setText(String.format("%s bulan", installmentTenor));

                    //set plafond sesuai dengan product yang dipilih
                    plafonMinMax.setText(String.format("Min %s - Max %s", CommonUtils.setRupiahCurrency(mServiceProducts.get(position).getMinLoan()),
                            CommonUtils.setRupiahCurrency(mServiceProducts.get(position).getMaxLoan())));

                    //set listener if user click back on the phone after type number of plafond
                    plafondCustom.setOnHideSoftKeyboardAction((keyCode, event) -> {

                        if (keyCode == KeyEvent.KEYCODE_BACK){

                            CalculateData(position, mServiceProducts);

                        }
                    });

                    //set listener if user click OK after type number of plafind
                    plafondCustom.setOnEditorActionListener((v, keyCode, event) -> {

                        if(keyCode == EditorInfo.IME_ACTION_DONE){

                            CalculateData(position, mServiceProducts);

                        }
                        return false;
                    });

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        } else {

            Toast.makeText(parentActivity(), "Data Produk Kosong", Toast.LENGTH_LONG).show();
            spProducts.setVisibility(View.GONE);
            lyDataProdukEmpty.setVisibility(View.VISIBLE);

        }

        dialog.dismiss();
    }

    private void CalculateData(int position, List<ProductsAgent> serviceProducts) {

        if(!plafondCustom.getText().toString().trim().isEmpty()){

            installment.setProgress(0);

            //get value from edittext to set plafond
            int nominal = Integer.parseInt(plafondCustom.getText().toString().replaceAll(",", ""));
            int nominalRound = roundingValue(nominal);

            //calculate asn value
            administration = calculateAdministration_v2(nominalRound, mServiceProducts.get(position).getFees());

            productID = mServiceProducts.get(position).getId();

            if(nominalRound < mServiceProducts.get(position).getMinLoan()){

                // jumlah pinjaman lebih kecil dari batas minimum
                Toast.makeText(parentActivity(), "Jumlah pinjmana lebih kecil dari batas minimum", Toast.LENGTH_SHORT).show();
                loanAmount = 0;
                interest = 0;
                totalBunga = 0;
                angsurnaPerbulan = 0;
                countPencairan = 0;

                biayaAdmin.setText("-");
                tvBunga.setText("-");
                tvAngsuran.setText("-");
                jumlahPencairan.setText("-");


            }else if(nominalRound > mServiceProducts.get(position).getMaxLoan()){

                // Jumlah pinhaman lebih besar dari batas maksimum
                Toast.makeText(parentActivity(), "Jumlah pinjmana lebih besar dari batas maximum", Toast.LENGTH_SHORT).show();
                loanAmount = 0;
                interest = 0;
                totalBunga = 0;
                angsurnaPerbulan = 0;
                countPencairan = 0;

                biayaAdmin.setText("-");
                tvBunga.setText("-");
                tvAngsuran.setText("-");
                jumlahPencairan.setText("-");


            }else {

                //acceptable
                Toast.makeText(parentActivity(), "Diterima", Toast.LENGTH_SHORT).show();

                //set rincian harga
                String value = plafondCustom.getText().toString().replaceAll(",", "");
                loanAmount = Integer.parseInt(value);

                //base on seekbar installment
                installmentTenor = minTenor;

                //calculate bunga
                interest = mServiceProducts.get(position).getInterest();
                totalBunga = (int) (loanAmount * interest) / 100;

                //calculate angsuran perbulan
                angsurnaPerbulan = calculateAngsuranPerBulan(convSetup);

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

        if(data.size() > 0) {

            spAlasanPinjam.setVisibility(View.VISIBLE);
            lyDataAlasanEmpty.setVisibility(View.GONE);

            List<String> chooser = new ArrayList<>();
            for (ReasonLoan.Data x : data) {
                if (x.getStatus().equals("active")) {
                    chooser.add(x.getName());
                }
            }

            ArrayAdapter<String> mAdapterAlasan = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, sortLoanPurpose(chooser));
            spAlasanPinjam.setAdapter(mAdapterAlasan);
            spAlasanPinjam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getSelectedItem().equals("Lain-lain")) {

                        lyAlasanLain.setVisibility(View.VISIBLE);
                    } else {
                        lyAlasanLain.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else {

            Toast.makeText(parentActivity(), "Data Alasan Peminjaman Kosong", Toast.LENGTH_SHORT).show();
            spAlasanPinjam.setVisibility(View.GONE);
            lyDataAlasanEmpty.setVisibility(View.VISIBLE);

        }
    }

    private ArrayList<String> sortLoanPurpose(List<String> data) {
        ArrayList<String> modelList = new ArrayList<>();
        boolean isOtherAvail = false;
        int arrayOthers = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).toLowerCase().equals("lain-lain")) {
                isOtherAvail = true;
                arrayOthers = i;
            } else {
                modelList.add(data.get(i));
            }
        }

        Collections.sort(modelList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });
        if (isOtherAvail) {
            modelList.add(data.get(arrayOthers));
        }
        return modelList;
    }

    @Override
    public void setupFormulaFee(String adminsetup, String convsetup) {

        adminSetup = adminsetup;

        convSetup = convsetup;

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

        return calAdminFee + calAsnFee;

    }

    private int calculateAdministration_v2(int plafon, List<FeesItem> fees){

        int result = 0;
        double tempCount;

        if(fees.size() > 0) {
            for (FeesItem param : fees) {

                if (param.getAmount().contains("%")) {

                    tempCount = Double.parseDouble(param.getAmount().replace("%", ""));
                    result = result + ((int) (plafon * tempCount) / 100);

                } else {

                    if (param.getAmount().toLowerCase().contains(".")) {

                        result = result + (int) Double.parseDouble(param.getAmount());

                    } else {

                        result = result + Integer.parseInt(param.getAmount());

                    }
                }

            }
        }

        return result;

    }

    @OnClick(R.id.plafondCustom)
    void onClickPlafondEdittext(){

        plafondCustom.setText("");
        loanAmount = 0;

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
            Toast.makeText(parentActivity(), "Mohon masukkan jumlah pinjaman dengan benar", Toast.LENGTH_SHORT).show();
            return;

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

            String loanLainlain = String.format("%s (%s)", "Lain-lain", etAlasan.getText().toString());
            intent.putExtra(SummaryTransactionActivity.ALASAN,  loanLainlain);

        } else {

            intent.putExtra(SummaryTransactionActivity.ALASAN, spAlasanPinjam.getSelectedItem().toString());

        }

        intent.putExtra(SummaryTransactionActivity.TUJUAN, etTujuan.getText().toString());
        intent.putExtra(SummaryTransactionActivity.LAYANAN, bundle.getInt("idService"));
        startActivity(intent);

    }

    @OnClick(R.id.refresh)
    void onClickRefreshProduct(){

        mPresenter.getProducts(idService);

    }

    @OnClick(R.id.refreshAlasan)
    void onClickRefreshAlasan(){

        mPresenter.getLoanIntention();

    }



    private double calculateAngsuranPerBulan(String jenisPotong) {

        if (jenisPotong.equals(POTONG_PLAFON)) {

            return (loanAmount + totalBunga) / installmentTenor;

        } else {

            return (loanAmount + totalBunga + administration) / installmentTenor;
        }

    }
}
