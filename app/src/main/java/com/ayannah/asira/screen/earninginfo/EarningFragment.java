package com.ayannah.asira.screen.earninginfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.dialog.BottomChangingIncome;
import com.ayannah.asira.screen.agent.loan.LoanAgentActivity;
import com.ayannah.asira.screen.loan.LoanActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EarningFragment extends BaseFragment implements EarningContract.View,
        BottomChangingIncome.BottomSheetChangingIncomeListener,
        Validator.ValidationListener {

    @Inject
    EarningContract.Presenter mPresenter;

    @NotEmpty(message = "Kolom ini wajib diisi")
    @BindView(R.id.etpenghasilam)
    EditText etPenghasilan;
    private String originalPenghaislan;

    @BindView(R.id.etPendapatanLain)
    EditText etPendapatanLain;
    private String originalStringPendapatanLain;

    @BindView(R.id.etSumberPendapatanLain)
    EditText etSumberPendapatanLain;

    @Inject
    String idService;

    private BottomChangingIncome popUpChangingIncome;
    private Validator validator;
    private AlertDialog dialog;
    UserBorrower userBorrower;

    @Inject
    public EarningFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_earning;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        if (getActivity().getIntent().getStringExtra("isFrom").toLowerCase().equals("agent")) {
//            Toast.makeText(parentActivity(), "dari Agent", Toast.LENGTH_SHORT).show();
            userBorrower = (UserBorrower) getActivity().getIntent().getSerializableExtra("user");
            loadPenghasilan(String.valueOf(userBorrower.getMonthlyIncome()),
                    String.valueOf(userBorrower.getOtherIncome()),
                    userBorrower.getOtherIncomesource());
        } else {
            mPresenter.getPenghasilan();
        }
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

        setUpPopUp();

        etPenghasilan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etPenghasilan.removeTextChangedListener(this);

                try {

                    originalPenghaislan = s.toString();

                    long longval;
                    if(originalPenghaislan.contains(",")){
                        originalPenghaislan = originalPenghaislan.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalPenghaislan);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to edittext
                    etPenghasilan.setText(formattedString);
                    etPenghasilan.setSelection(etPenghasilan.getText().length());

                } catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }

                etPenghasilan.addTextChangedListener(this);

            }
        });

        etPendapatanLain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etPendapatanLain.removeTextChangedListener(this);

                try {

                    originalStringPendapatanLain = s.toString();

                    long longval;
                    if(originalStringPendapatanLain.contains(",")){
                        originalStringPendapatanLain = originalStringPendapatanLain.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalStringPendapatanLain);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to edittext
                    etPendapatanLain.setText(formattedString);
                    etPendapatanLain.setSelection(etPendapatanLain.getText().length());

                } catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }

                etPendapatanLain.addTextChangedListener(this);

            }
        });

    }

    private void setUpPopUp() {

    }

    @OnClick(R.id.buttonNext)
    void onClickProcess(){

        validator.validate();

    }

    @Override
    public void showErrorMessage(String message) {

        dialog.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadPenghasilan(String pendapatan, String pendapatanLain, String sumberLain) {

        popUpChangingIncome = new BottomChangingIncome();

        popUpChangingIncome.setListenerBottomChangingIncome(this);

        popUpChangingIncome.setIncomeUser(Integer.parseInt(pendapatan), Integer.parseInt(pendapatanLain), sumberLain);

        popUpChangingIncome.showNow(getChildFragmentManager(), "pop up");

        etPenghasilan.setText(pendapatan);

        etPendapatanLain.setText(pendapatanLain);

        etSumberPendapatanLain.setText(sumberLain);

    }

    @Override
    public void completeUpdateIncome() {
        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;

        dialog.dismiss();
        Intent intent;
//        intent.putExtra("idService", bundle.getInt("id"));
        if (getActivity().getIntent().getStringExtra("isFrom").toLowerCase().equals("agent")) {
            intent = new Intent(parentActivity(), LoanAgentActivity.class);
            intent.putExtra("isFrom", "agent");
            intent.putExtra("user", (Serializable) userBorrower);
            intent.putExtra(LoanAgentActivity.IDSERVICE, idService);
            intent.putExtra(LoanAgentActivity.IDBANK, "1");
        } else {
            intent= new Intent(parentActivity(), LoanActivity.class);
        }
        intent.putExtra(LoanActivity.IDSERVICE, idService);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void onClickNo() {
        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;

        Intent pinjaman;
        if (getActivity().getIntent().getStringExtra("isFrom").toLowerCase().equals("agent")) {
            pinjaman = new Intent(parentActivity(), LoanAgentActivity.class);
            pinjaman.putExtra("user", (Serializable) userBorrower);
            pinjaman.putExtra("isFrom", "agent");
            pinjaman.putExtra(LoanAgentActivity.IDSERVICE, idService);
            pinjaman.putExtra(LoanAgentActivity.IDBANK, "1");
        } else {
            pinjaman = new Intent(parentActivity(), LoanActivity.class);
//        pinjaman.putExtra("idService", bundle.getInt("id"));
            pinjaman.putExtra(LoanActivity.IDSERVICE, idService);
        }

        popUpChangingIncome.dismiss();
        startActivity(pinjaman);

    }

    @Override
    public void onClickYes() {
        popUpChangingIncome.dismiss();

    }

    @Override
    public void onValidationSucceeded() {

        int primary = Integer.parseInt(originalPenghaislan);
        int secondaru = 0;
        if(originalStringPendapatanLain != null ){
            secondaru = Integer.parseInt(originalStringPendapatanLain);
        }

        String others = etSumberPendapatanLain.getText().toString().trim();

        dialog.show();
        mPresenter.updateUserIncome(primary, secondaru, others);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
