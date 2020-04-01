package com.ayannah.asira.screen.agent.loan;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import com.ayannah.asira.R;
import com.ayannah.asira.custom.PlafondEditText;
import com.ayannah.asira.data.model.FeesItem;
import com.ayannah.asira.data.model.FormDynamic;
import com.ayannah.asira.data.model.Installments;
import com.ayannah.asira.data.model.Products;
import com.ayannah.asira.data.model.ProductsAgent;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.data.model.ServiceProductsAgent;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.dialog.DialogTableInstallment;
import com.ayannah.asira.screen.summary.SummaryTransactionActivity;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.Interest;
import com.ayannah.asira.util.NumberSeparatorTextWatcher;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

//    @BindView(R.id.amountBunga)
//    TextView tvBunga;

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

    @BindView(R.id.txtOtherReasonTitle)
    TextView txtOtherReasonTitle;

    @BindView(R.id.txtReasonTitle)
    TextView txtReasonTitle;

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
    private int feeDeducted = 0;
    private int feeCharged = 0;

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
    private ArrayList<ProductsAgent> mServiceProducts = new ArrayList<>();
    private NumberSeparatorTextWatcher plafonNumberSeparator;
    private ArrayList<Installments> arInstallments = new ArrayList<>();
    private List<EditText> allEds = new ArrayList<EditText>();
    private List<Spinner> allSPs = new ArrayList<Spinner>();
    private List<ImageView> allIVs = new ArrayList<ImageView>();
    private List<CheckBox> allCBs = new ArrayList<CheckBox>();
    private List<TextView> allTVCBs = new ArrayList<TextView>();
    private List<FormDynamic> arrForm = new ArrayList<FormDynamic>();
    private ArrayList<FormDynamic> arrFormForSend = new ArrayList<FormDynamic>();
    private int imgID = 0;
    UserBorrower userBorrower;

    @Inject
    public LoanAgentFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_loan;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void initView(Bundle state) {mPresenter.takeView(this);

        txtOtherReasonTitle.setText("Mohon Masukan Alasan Nasabah Baru Meminjam");
        txtReasonTitle.setText("Apa alasan nasabah baru mengajukan peminjaman?");

        if (getActivity().getIntent().getStringExtra("isFrom").toLowerCase().equals("agent")) {
            userBorrower = (UserBorrower) getActivity().getIntent().getSerializableExtra("user");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();
        mPresenter.getProducts(idService);

        mPresenter.getLoanIntention();

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
                installmentTenor = minTenor + seekBar.getProgress();
                tvInstallment.setText(String.format("%s bulan", installmentTenor));

                CreateCalculationData();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void CreateCalculationData() {
        arInstallments.clear();

        if (!plafondCustom.getText().toString().trim().isEmpty()) {

            createTableAngsuran(interest, loanAmount, installmentTenor, administration, interestType, feeType);

            biayaAdmin.setText(CommonUtils.setRupiahCurrency((int) Math.round(administration)));
            if (arInstallments.get(0).getAngsuranPerBulan() == null) {
                tvAngsuran.setText(Html.fromHtml("<u>Lihat Tabel</u>"));
                tvAngsuran.setTextColor(getResources().getColor(R.color.textColorAsira));
                arInstallments.remove(0);
                tvAngsuran.setClickable(true);
            } else {
                tvAngsuran.setText(CommonUtils.setRupiahCurrency((int) Math.round(Double.parseDouble(arInstallments.get(0).getAngsuranPerBulan()))));
                tvAngsuran.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
                tvAngsuran.setClickable(false);
            }

//            jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) Math.round(countPencairan)));
            jumlahPencairan.setText(CommonUtils.setRupiahCurrency((int) (loanAmount - feeDeducted)));

        } else {

            installment.setProgress(0);
        }
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
                double ansuranPerBulan = calculateAngsuranPerBulan(interestLoc, cicilanPokokPinjaman, administration, installmentTenor, feeDeducted, feeCharged, type);

                Installments installmentsFlat = new Installments();
                installmentsFlat.setBunga(formatStringDouble(interestLoc * installmentTenor));
                installmentsFlat.setCicilanPokokPinjaman(formatStringDouble(cicilanPokokPinjaman));
                installmentsFlat.setAngsuranPerBulan(formatStringDouble(ansuranPerBulan));
                angsurnaPerbulan = ansuranPerBulan;

                arInstallments.add(installmentsFlat);

                break;
            }
            case "fixed": {
                double[] returnPPMT = Interest.PPMT(interest / 100 / 12, 1, installmentTenor, loanAmount * (-1), 1);
                double ansuranPerBulan = calculateAngsuranPerBulan(returnPPMT[1], returnPPMT[0], administration, installmentTenor, feeDeducted, feeCharged, type);

                Installments installmentsFixed = new Installments();
                installmentsFixed.setCicilanPokokPinjaman(formatStringDouble(returnPPMT[0]));
                installmentsFixed.setAngsuranPerBulan(String.valueOf(ansuranPerBulan));
                angsurnaPerbulan = ansuranPerBulan;

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
                    installments.setAngsuranPerBulan(formatStringDouble(calculateAngsuranPerBulan(interestLoc, cicilanPokokPinjaman, administration, installmentTenor, feeDeducted, feeCharged, type)));
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
                angsurnaPerbulan = AngsuranPerBulanOneTimePay(interest, loanAmount, installmentTenor);

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

    private double calculateAngsuranPerBulan(double interestLoc, double cicilanPokokPinjaman, int administration, int installmentTenor, int feeDeductedLoc, int feeChargedLoc, String type) {
        return interestLoc + cicilanPokokPinjaman + ((double) feeChargedLoc/installmentTenor);
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
                    int maxTenorSeekbar = maxTenor-minTenor;
                    installment.setMax(maxTenorSeekbar);

                    interestType = mServiceProducts.get(position).getInterest_type();
//                    feeType = mServiceProducts.get(position).getFees().get(0).getFeeMethod();

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
        allCBs.clear();
        allTVCBs.clear();
        arrFormForSend.clear();
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
                    Toast.makeText(parentActivity(), String.format("%s not defined", arrForm.get(i).getType()), Toast.LENGTH_LONG).show();
                    break;
            }
            arrFormForSend.add(arrForm.get(i));
        }
    }

    private void CalculateData(int position, List<ProductsAgent> serviceProducts) {

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
//                tvBunga.setText("-");
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
//                tvBunga.setText("-");
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
//                angsurnaPerbulan = AngsuranPerBulanOneTimePay(convSetup);

                if (convSetup.equals(POTONG_PLAFON)) {
                    countPencairan = loanAmount - administration;
                } else {
                    countPencairan = loanAmount;
                }

                CreateCalculationData();

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

    private int calculateAdministration_v2(int plafon, List<FeesItem> fees){

        feeDeducted = 0;
        feeCharged = 0;
        int result = 0;
        double tmptResult;

        if(fees.size() > 0) {
            for (FeesItem param : fees) {

                if (param.getAmount().contains("%")) {

                    double tempCount = Double.parseDouble(param.getAmount().replace("%", ""));
                    tmptResult = (plafon * tempCount) / 100;
                    result = result + (int) tmptResult;

                } else {

                    if (param.getAmount().toLowerCase().contains(".")) {
                        tmptResult = Double.parseDouble(param.getAmount());
                        result = result + (int) tmptResult;

                    } else {
                        tmptResult = Double.parseDouble(param.getAmount());
                        result = result + (int) tmptResult;

                    }
                }

                if (param.getFeeMethod().toLowerCase().equals(POTONG_PLAFON)) {
                    feeDeducted = (int) (feeDeducted + tmptResult);
                } else {
                    feeCharged = (int) (feeCharged + tmptResult);
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

        if (cvForm.getVisibility() == View.VISIBLE && !CheckMandatoryDynamic(allEds , allSPs, allIVs, allTVCBs)) {
            Toast.makeText(parentActivity(), "Data Tambahan ada yg belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(parentActivity(), SummaryTransactionActivity.class);

        if (getActivity().getIntent().getStringExtra("isFrom").toLowerCase().equals("agent")) {
            intent.putExtra(SummaryTransactionActivity.BORROWERID, userBorrower.getId());
            intent.putExtra(SummaryTransactionActivity.BANKACCOUNTNUMBER, userBorrower.getBankAccountnumber());
        }
        intent.putExtra(SummaryTransactionActivity.PINJAMAN, loanAmount);
        intent.putExtra(SummaryTransactionActivity.TENOR, installmentTenor);
        intent.putExtra(SummaryTransactionActivity.ANGSURAN_BULAN, angsurnaPerbulan);
        intent.putExtra(SummaryTransactionActivity.PRODUK, spProducts.getSelectedItem().toString());
        intent.putExtra(SummaryTransactionActivity.PRODUCTID, productID);
        intent.putExtra(SummaryTransactionActivity.ADMIN, administration);
//        intent.putExtra(SummaryTransactionActivity.INTEREST, totalBunga);
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
        intent.putExtra(SummaryTransactionActivity.INSTALLMENT, arInstallments);
        intent.putExtra(SummaryTransactionActivity.FORMINFO, arrFormForSend);
        startActivity(intent);

    }

    private boolean CheckMandatoryDynamic(List<EditText> allEds, List<Spinner> allSPs, List<ImageView> allIVs, List<TextView> allTVCBs) {
        for (EditText et : allEds) {
            if (et.getTag().equals("required") && et.getText().toString().equals("")) {
                et.setError("wajib");
                return false;
            } else {
                for (int i=0; i<arrFormForSend.size(); i++) {
                    if (arrFormForSend.indexOf(arrFormForSend.get(i))+1 == et.getId() ) {
                        arrFormForSend.get(i).setAnswers(et.getText().toString());
                    }
                }
            }
        }

        for (Spinner sp : allSPs) {
            if (sp.getTag().equals("required") && sp.getSelectedItemPosition() == 0) {
                return false;
            } else {
                for (int i=0; i<arrFormForSend.size(); i++) {
                    if (arrFormForSend.indexOf(arrFormForSend.get(i))+1 == sp.getId() && sp.getSelectedItemPosition() != 0) {
                        arrFormForSend.get(i).setAnswers(sp.getSelectedItem().toString());
                    }
                }
            }
        }

        for (ImageView iv : allIVs) {
            if (iv.getTag().equals("required") && iv.getDrawable() == null) {
                return false;
            } else {
                for (int i=0; i<arrFormForSend.size(); i++) {
                    if (arrFormForSend.indexOf(arrFormForSend.get(i))+1 == iv.getId() && iv.getDrawable() != null) {
                        Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();

                        String pict64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);

                        arrFormForSend.get(i).setAnswers(pict64);
                    }
                }
            }
        }

        for (TextView tvcb : allTVCBs) {
            if (tvcb.getTag().toString().contains("required") && Integer.parseInt(tvcb.getTag().toString().substring(0,1)) == 0){
                return false;
            } else {
                for (int i=0; i<arrFormForSend.size(); i++) {
                    if (arrFormForSend.indexOf(arrFormForSend.get(i))+1 == tvcb.getId() && Integer.parseInt(tvcb.getTag().toString().substring(0,1)) != 0) {
                        List<String> selected = new ArrayList<>();
                        for (int j=0; j<allCBs.size(); j++) {
                            if (allCBs.get(j).isChecked() && allCBs.get(j).getId() == arrFormForSend.indexOf(arrFormForSend.get(i))+1) {
                                selected.add(allCBs.get(j).getText().toString());
                            }
                        }

                        StringBuilder sb = new StringBuilder();
                        for (String s : selected) {
                            sb.append(s).append(",");
                        }

                        if (sb.length() != 0) {
                            arrFormForSend.get(i).setAnswers(sb.deleteCharAt(sb.length()-1).toString());
                        }
                    }
                }
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

        mPresenter.getLoanIntention();

    }



    private double AngsuranPerBulanOneTimePay(double interest, int loanAmount, int installmentTenor) {

        double interestLoc = loanAmount*interest/100;
        return (loanAmount + interestLoc + feeCharged)/installmentTenor;

    }

    private void createEditText(@NonNull FormDynamic form, int index) {
        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 20, 0, 5);
        tv.setLayoutParams(paramTV);

//       just devider ==============================================================================

        EditText et = new EditText(parentActivity());
        et.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.focus_tint_list)));
        et.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        et.setTextSize(getResources().getDimension(R.dimen._4sdp));
        et.setTag(form.getStatus());
        et.setId(index);
        et.setHint(String.format("Masukan %s", tv.getText()));
        llForm.addView(et);
        LinearLayout.LayoutParams paramET = (LinearLayout.LayoutParams) et.getLayoutParams();
        paramET.setMargins(0, 5, 0, 5);
        et.setLayoutParams(paramET);

        if (et.getTag().toString().contains("required")) {
            tv.append(getResources().getString(R.string.wajib_isi));
        }

        allEds.add(et);
    }

    private void createDropDown(@NonNull FormDynamic form, int index) {
        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 20, 0, 5);
        tv.setLayoutParams(paramTV);

//       just devider ==============================================================================

        List<String> value = new ArrayList<>();
        value.add("Pilih...");
        value.addAll(Arrays.asList(form.getValue().split(",|\\:")));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, value);

        Spinner sp = new Spinner(parentActivity());
        sp.setBackgroundResource(R.drawable.spinner_bg2);
        sp.setId(index);
        sp.setTag(form.getStatus());
        sp.setAdapter(adapter);
        llForm.addView(sp);
        LinearLayout.LayoutParams paramSP = (LinearLayout.LayoutParams) sp.getLayoutParams();
        paramSP.setMargins(0, 5, 0, 5);
        sp.setLayoutParams(paramSP);

        if (sp.getTag().toString().contains("required")) {
            tv.append(getResources().getString(R.string.wajib_isi));
        }

        allSPs.add(sp);
    }

    private void createCheckBox(@NonNull FormDynamic form, int index) {
        int[] numChecked = new int[1];

        TextView tv = new TextView(parentActivity());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        tv.setTag(String.format("%s %s", String.valueOf(numChecked[0]), form.getStatus()));
        tv.setText(form.getLabel());
        tv.append(getResources().getString(R.string.wajib_isi));
        tv.setId(index);
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 20, 0, 5);
        tv.setLayoutParams(paramTV);

        allTVCBs.add(tv);

//       just devider ==============================================================================

        int arrCount=0;
        List<String> value = new ArrayList<String>(Arrays.asList(form.getValue().split(",|\\:")));
//        List<String> value = new ArrayList<>(Arrays.asList(form.getValue()));

        arrCount = value.size();

        for (int i = 0; i < arrCount; i++)
        {
            TableRow row =new TableRow(parentActivity());
            row.setId(index);
            row.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            CheckBox checkBox = new CheckBox(parentActivity());
            checkBox.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.textColorAsira)));
            checkBox.setId(index);
            checkBox.setText(value.get(i));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        numChecked[0]++;
                    } else {
                        numChecked[0]--;
                    }
                    tv.setTag(String.format("%s %s", String.valueOf(numChecked[0]), form.getStatus()));

                }
            });

            row.addView(checkBox);
            llForm.addView(row);
            LinearLayout.LayoutParams paramSP = (LinearLayout.LayoutParams) checkBox.getLayoutParams();
            paramSP.setMargins(0, 5, 0, 5);
            checkBox.setLayoutParams(paramSP);

            allCBs.add(checkBox);
        }
    }

    private void createTakePicture(@NonNull FormDynamic form, int index) {

        TextView tv = new TextView(parentActivity());
        tv.setText(form.getLabel());
        tv.setTextColor(getResources().getColor(R.color.textColorAsiraGrey));
        llForm.addView(tv);
        LinearLayout.LayoutParams paramTV = (LinearLayout.LayoutParams) tv.getLayoutParams();
        paramTV.setMargins(0, 20, 0, 5);
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

        if (iv.getTag().toString().contains("required")) {
            tv.append(getResources().getString(R.string.wajib_isi));
        }

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
