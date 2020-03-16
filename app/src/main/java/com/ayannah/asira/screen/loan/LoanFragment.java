package com.ayannah.asira.screen.loan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.PlafondEditText;
import com.ayannah.asira.data.model.FeesItem;
import com.ayannah.asira.data.model.FormDynamic;
import com.ayannah.asira.data.model.Installments;
import com.ayannah.asira.data.model.Products;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.dialog.DialogTableInstallment;
import com.ayannah.asira.screen.summary.SummaryTransactionActivity;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.Interest;
import com.ayannah.asira.util.NumberSeparatorTextWatcher;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ayannah.asira.R2.id.design_bottom_sheet;

public class LoanFragment extends BaseFragment implements LoanContract.View {

    @Inject
    LoanContract.Presenter mPresenter;

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

    @BindView(R.id.llForm)
    LinearLayout llForm;

    @BindView(R.id.cvForm)
    CardView cvForm;

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
    private String interestType = "";
    private String feeType = "";

    //define min and max loan var globally based on selected product loan
    private int minPlafond = 0;
    private int maxPlafond = 0;

    //define min and max tenor var globally based on selected product loan
    private int minTenor = 0;
    private int maxTenor = 0;

    //define adminfee and convfee setup
    private static final String POTONG_PLAFON = "deduct_loan";
    private static final String BEBAN_PLAFON = "charge_loan";
    private String adminSetup;
    private String convSetup = POTONG_PLAFON;

    private List<String> productName;
    private ArrayList<Products> mServiceProducts = new ArrayList<>();
    private NumberSeparatorTextWatcher plafonNumberSeparator;
    private ArrayList<Installments> arInstallments = new ArrayList<>();
    private List<EditText> allEds = new ArrayList<EditText>();
    private List<Spinner> allSPs = new ArrayList<Spinner>();
    private List<ImageView> allIVs = new ArrayList<ImageView>();
    private List<FormDynamic> arrForm = new ArrayList<FormDynamic>();
    private int imgID = 0;

    @Inject
    public LoanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_loan;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void initView(Bundle state) {
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();
        mPresenter.getProducts(idService);

        mPresenter.getReasonLoan();

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
                installmentTenor = minTenor + (seekBar.getProgress() * 6);
                tvInstallment.setText(String.format("%s bulan", installmentTenor));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                arInstallments.clear();

                if (!plafondCustom.getText().toString().trim().isEmpty()) {
//                    //calculate bunga
//                    totalBunga = calculateInterest(loanAmount, installmentTenor, interest, "flat");
//                    //calculate angsuran perbulan
//                    angsurnaPerbulan = finalAngsuranPerBulan(convSetup);

//                    createTableAngsuran(interest, loanAmount, installmentTenor, administration, "flat", POTONG_PLAFON);
//                    createTableAngsuran(6.0, 12000000, 24, administration, "fixed", POTONG_PLAFON);
//                    createTableAngsuran(6.0, 12000000, 24, administration, "efektif_menurun", POTONG_PLAFON);
                    createTableAngsuran(interest, loanAmount, installmentTenor, administration, interestType, feeType);

                    biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
                    if (arInstallments.get(0).getAngsuranPerBulan() == null) {
                        tvAngsuran.setText("Lihat Tabel");
                        tvAngsuran.setTextColor(getResources().getColor(R.color.textColorAsira));
                        arInstallments.remove(0);
                        tvAngsuran.setClickable(true);
                    } else {
                        tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(Double.parseDouble(arInstallments.get(0).getAngsuranPerBulan()))));
                        tvAngsuran.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
                        tvAngsuran.setClickable(false);
                    }

                    jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) Math.floor(countPencairan)));

                } else {

                    installment.setProgress(0);
                }

            }
        });

    }

    @OnClick(R.id.angsuranPerbulan)
    void clickTable() {
        DialogTableInstallment dialog = new DialogTableInstallment();
        dialog.setInstallments(arInstallments);
        dialog.showNow(parentActivity().getSupportFragmentManager(), "installmentTable");

    }

    private void createTableAngsuran(double interest, int loanAmount, int installmentTenor, int administration, String type, String feeType) {

        switch (type) {
            case "flat": {
                int interestLoc = calculateInterest(loanAmount, installmentTenor, interest, type);
                double cicilanPokokPinjaman = loanAmount / installmentTenor;
                double ansuranPerBulan = calculateAngsuranPerBulan(interestLoc, cicilanPokokPinjaman, administration, installmentTenor, feeType);

                Installments installmentsFlat = new Installments();
                installmentsFlat.setBunga(formatStringDouble(interestLoc * installmentTenor));
                installmentsFlat.setCicilanPokokPinjaman(formatStringDouble(cicilanPokokPinjaman));
                installmentsFlat.setAngsuranPerBulan(formatStringDouble(ansuranPerBulan));

                arInstallments.add(installmentsFlat);

                break;
            }
            case "fixed": {
                double[] returnPPMT = Interest.PPMT(interest / 100 / 12, 1, installmentTenor, loanAmount * (-1), 1);
                double ansuranPerBulan = calculateAngsuranPerBulan(returnPPMT[1], returnPPMT[0], administration, installmentTenor, feeType);

                Installments installmentsFixed = new Installments();
                installmentsFixed.setCicilanPokokPinjaman(formatStringDouble(returnPPMT[0]));
                installmentsFixed.setAngsuranPerBulan(String.valueOf(ansuranPerBulan));

                arInstallments.add(installmentsFixed);

                break;
            }
            case "efektif_menurun": {
                double cicilanPokokPinjaman = loanAmount / installmentTenor;

                Installments installmentsFixed = new Installments();
                installmentsFixed.setAngsuranPerBulan(null);

                arInstallments.add(installmentsFixed);

                int loanAmountLoc = loanAmount;
                for (int i = 0; i < installmentTenor; i++) {
                    double interestLoc = 0;

                    Installments installments = new Installments();
                    installments.setIndex(i + 1);
                    installments.setPokokPinjaman(formatStringDouble(loanAmount - cicilanPokokPinjaman * i));
                    installments.setCicilanPokokPinjaman(formatStringDouble(cicilanPokokPinjaman));
                    installments.setBunga(formatStringDouble(Double.parseDouble(installments.getPokokPinjaman()) * interest / 100 / 12));
                    interestLoc = loanAmountLoc * interest / 100 / 12;
                    installments.setAngsuranPerBulan(formatStringDouble(calculateAngsuranPerBulan(interestLoc, cicilanPokokPinjaman, administration, installmentTenor, feeType)));
                    installments.setSaldoPokokPinjaman(formatStringDouble(loanAmount - cicilanPokokPinjaman * (i + 1)));
                    loanAmountLoc = Integer.parseInt(installments.getSaldoPokokPinjaman());

                    arInstallments.add(installments);
                }

                break;
            }
            case "onetimepay": {
                    //calculate bunga
                    totalBunga = calculateInterest(loanAmount, installmentTenor, interest, "onetimepay");
                    //calculate angsuran perbulan
                    angsurnaPerbulan = finalAngsuranPerBulan(convSetup);

                    Installments installments = new Installments();
                    installments.setIndex(0);
                    installments.setAngsuranPerBulan(String.valueOf(angsurnaPerbulan));

                    arInstallments.add(installments);

                break;
            }
        }

    }

    private String formatStringDouble(double value) {
        return new DecimalFormat("#.#").format(value);
    }

    private double calculateAngsuranPerBulan(double interestLoc, double cicilanPokokPinjaman, int administration, int installmentTenor, String feeType) {

        if (feeType.equals(POTONG_PLAFON)) {
            return interestLoc + cicilanPokokPinjaman;
        } else {
            return interestLoc + cicilanPokokPinjaman + (administration/installmentTenor);
        }
    }

    private int calculateInterest(int loanAmount, int installmentTenor, double interest, String type) {

        if (type.equals("flat")) {
            return (int) (loanAmount*interest/100/12);
        } else if (type.equals("onetimepay")) {
            return (int) (loanAmount*interest/100);
        } else {
            return 0;
        }
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

                    interestType = mServiceProducts.get(position).getInterest_type();
                    feeType = mServiceProducts.get(position).getFees().get(0).getFeeMethod();

                    installmentTenor = minTenor;
//                    tvInstallment.setText(String.format("%s bulan", installmentTenor));

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

                    if (mServiceProducts.get(position).getFormDynamic() != null) {
                        createDynamicForm(mServiceProducts.get(position).getFormDynamic());
                        cvForm.setVisibility(View.VISIBLE);
                    } else {
                        cvForm.setVisibility(View.GONE);
                    }

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

    private void createDynamicForm(List<FormDynamic> formDynamic) {
        allEds.clear();
        allSPs.clear();
        allIVs.clear();
        llForm.removeAllViews();

        arrForm = new ArrayList<>(formDynamic);

        for (int i=0; i< arrForm.size(); i++) {
            String type = arrForm.get(i).getType();

            switch (type) {
                case "textfield":
                    createEditText(arrForm.get(i), i + 1);
                    break;
                case "dropdown":
                    createDropDown(arrForm.get(i), i + 1);
                    break;
                case "checkbox":
                    createCheckBox(arrForm.get(i), i + 1);
                    break;
                case "image":
                    createTakePicture(arrForm.get(i), i + 1);
                    break;
                default:
                    Toast.makeText(parentActivity(), String.format("%s not defined", arrForm.get(i).getValue()), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void CalculateData(int position, List<Products> serviceProducts) {

        if(!plafondCustom.getText().toString().trim().isEmpty()){

            installment.setProgress(0);

            //get value from edittext to set plafond
            int nominal = Integer.parseInt(CommonUtils.removeDelimeter(plafondCustom.getText().toString()));
            int nominalRound = roundingValue(nominal);

            //calculate asn value
            administration = calculateAdministration_v2(nominalRound, mServiceProducts.get(position).getFees());

            productID = mServiceProducts.get(position).getId();

            if(nominalRound < mServiceProducts.get(position).getMinLoan()){

                // jumlah pinjaman lebih kecil dari batas minimum
                Toast.makeText(parentActivity(), "Jumlah pinjaman lebih kecil dari batas minimum", Toast.LENGTH_SHORT).show();
                loanAmount = 0;
                interest = 0;
                totalBunga = 0;
                angsurnaPerbulan = 0;
                countPencairan = 0;

                biayaAdmin.setText("-");
                tvAngsuran.setText("-");
                jumlahPencairan.setText("-");
                plafondCustom.setText("");


            }else if(nominalRound > mServiceProducts.get(position).getMaxLoan()){

                // Jumlah pinhaman lebih besar dari batas maksimum
                Toast.makeText(parentActivity(), "Jumlah pinjaman lebih besar dari batas maximum", Toast.LENGTH_SHORT).show();
                loanAmount = 0;
                interest = 0;
                totalBunga = 0;
                angsurnaPerbulan = 0;
                countPencairan = 0;

                biayaAdmin.setText("-");
                tvAngsuran.setText("-");
                jumlahPencairan.setText("-");
                plafondCustom.setText("");


            }else {

                //acceptable
                Toast.makeText(parentActivity(), "Diterima", Toast.LENGTH_SHORT).show();

                //set rincian harga
                String value = CommonUtils.removeDelimeter(plafondCustom.getText().toString());
                loanAmount = Integer.parseInt(value);

                //base on seekbar installment
                installmentTenor = minTenor;

                //calculate bunga
                interest = mServiceProducts.get(position).getInterest();
                totalBunga = (int) (loanAmount * interest) / 100;

                //calculate angsuran perbulan
                angsurnaPerbulan = finalAngsuranPerBulan(convSetup);

                if (convSetup.equals(POTONG_PLAFON)) {
                    countPencairan = loanAmount - administration;
                } else {
                    countPencairan = loanAmount;
                }
//
//                tvInstallment.setText(String.format("%s bulan", installmentTenor));
//                biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.floor(administration)));
//                tvBunga.setText(CommonUtils.setRupiahCurrency((int) Math.floor(totalBunga)));
//                tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.floor(angsurnaPerbulan)));
//                jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) Math.floor(countPencairan)));
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
            Toast.makeText(parentActivity(), "Mohon masukan jumlah pinjaman dengan benar", Toast.LENGTH_SHORT).show();
            return;

        }

        if (!CheckMandatoryDynamic(allEds , allSPs, allIVs)) {
            Toast.makeText(parentActivity(), "Form mandatory dynamic ada yg belum diisi", Toast.LENGTH_SHORT).show();
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

            String otherLainLain = String.format("%s (%s)", spAlasanPinjam.getSelectedItem().toString(), etAlasan.getText().toString());
            intent.putExtra(SummaryTransactionActivity.ALASAN,  otherLainLain);

        } else {

            intent.putExtra(SummaryTransactionActivity.ALASAN, spAlasanPinjam.getSelectedItem().toString());


        }

        intent.putExtra(SummaryTransactionActivity.TUJUAN, etTujuan.getText().toString());
        intent.putExtra(SummaryTransactionActivity.LAYANAN, bundle.getInt("idService"));
        startActivity(intent);

    }

    private boolean CheckMandatoryDynamic(List<EditText> allEds, List<Spinner> allSPs, List<ImageView> allIVs) {
        for (EditText et : allEds) {
            if (et.getTag().equals("required") && et.getText().toString().equals("")) {
                return false;
            }
        }

//        for (Spinner sp : allSPs) {
//            if (sp.getTag().equals("required")) {
//                return false;
//            }
//        }

        for (ImageView iv : allIVs) {
            if (iv.getTag().equals("required") && iv.getDrawable() != null) {
                return false;
            }
        }
        return true;
    }

    @OnClick(R.id.refresh)
    void onClickRefreshProduct(){

        mPresenter.getProducts(idService);

    }

    @OnClick(R.id.refreshAlasan)
    void onClickRefreshAlasan(){

        mPresenter.getReasonLoan();

    }



    private double finalAngsuranPerBulan(String jenisPotong) {

        if (jenisPotong.equals(POTONG_PLAFON)) {

            return (loanAmount + totalBunga) / installmentTenor;

        } else {

            return (loanAmount + totalBunga + administration) / installmentTenor;
        }

    }

    private void createEditText(@NonNull FormDynamic form, int index) {
        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 15, 0, 5);
        tv.setLayoutParams(paramTV);

//       just devider ==============================================================================

        EditText et = new EditText(parentActivity());
        et.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        et.setTextSize(getResources().getDimension(R.dimen._6sdp));
        et.setTag(form.getStatus());
        et.setId(index);
        et.setHint(String.format("Masukan %s", tv.getText()));
        llForm.addView(et);
        LinearLayout.LayoutParams paramET = (LinearLayout.LayoutParams) et.getLayoutParams();
        paramET.setMargins(0, 5, 0, 5);
        et.setLayoutParams(paramET);

        allEds.add(et);
    }

    private void createDropDown(@NonNull FormDynamic form, int index) {
        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 15, 0, 5);
        tv.setLayoutParams(paramTV);

//       just devider ==============================================================================

        List<String> value = new ArrayList<String>(Arrays.asList(form.getValue().split(",|\\:")));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(parentActivity(), R.layout.item_custom_spinner, value);

        Spinner sp = new Spinner(parentActivity());
        sp.setBackgroundResource(R.drawable.spinner_bg2);
        sp.setId(index);
        sp.setTag(form.getStatus());
        sp.setAdapter(adapter);
        llForm.addView(sp);
        LinearLayout.LayoutParams paramSP = (LinearLayout.LayoutParams) sp.getLayoutParams();
        paramSP.setMargins(0, 5, 0, 5);
        sp.setLayoutParams(paramSP);

        allSPs.add(sp);
    }

    private void createCheckBox(@NonNull FormDynamic form, int index) {
        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 15, 0, 5);
        tv.setLayoutParams(paramTV);

//       just devider ==============================================================================

        int arrCount=0;
        List<String> value = new ArrayList<String>(Arrays.asList(form.getValue().split(",|\\:")));

        arrCount = value.size();

        for (int i = 0; i < arrCount; i++)
        {
            TableRow row =new TableRow(parentActivity());
            row.setId(i);
            row.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            CheckBox checkBox = new CheckBox(parentActivity());
//            checkBox.setOnCheckedChangeListener(parentActivity());
            checkBox.setId(index);
            checkBox.setText(value.get(i));
            row.addView(checkBox);
            LinearLayout.LayoutParams paramSP = (LinearLayout.LayoutParams) checkBox.getLayoutParams();
            paramSP.setMargins(0, 5, 0, 5);
            checkBox.setLayoutParams(paramSP);

            llForm.addView(row);
        }
    }

    private void createTakePicture(@NonNull FormDynamic form, int index) {

        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 15, 0, 5);
        tv.setLayoutParams(paramTV);

//       just devider ==============================================================================

        ImageView iv = new ImageView(parentActivity());
        iv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 400));
        iv.setBackground(getResources().getDrawable(R.drawable.border_box));
        iv.setTag(form.getStatus());
        iv.setId(index);
        llForm.addView(iv);
        LinearLayout.LayoutParams paramSP = (LinearLayout.LayoutParams) iv.getLayoutParams();
        paramSP.setMargins(0, 5, 0, 5);
        iv.setLayoutParams(paramSP);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgID = index;
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 1);
            }
        });

        allIVs.add(iv);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            for (int i=0; i<allIVs.size(); i++) {
                if (allIVs.get(i).getId() == imgID) {
                    allIVs.get(i).setImageBitmap(imageBitmap);
                }
            }
            imgID = 0;
        }
    }
}
