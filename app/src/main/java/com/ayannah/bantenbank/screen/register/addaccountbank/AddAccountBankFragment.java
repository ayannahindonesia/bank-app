package com.ayannah.bantenbank.screen.register.addaccountbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.dialog.BottomSheetDialogGlobal;
import com.ayannah.bantenbank.screen.register.adddoc.AddDocumentActivity;
import com.ayannah.bantenbank.screen.register.adddoc.AddDocumentFragment;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAccountBankFragment extends BaseFragment implements AddAccountBankContract.View, Validator.ValidationListener {

    private AddDocumentFragment fragmentadd = new AddDocumentFragment();

    BottomSheetDialogGlobal dialog;

    @BindView(R.id.bankName)
    TextView bankName;
    String bName = null;

    @NotEmpty(message = "Masukan nomor rekening")
    @BindView(R.id.regist_accNumber)
    EditText accNumber;

    private Validator validator;

    @Inject
    public AddAccountBankFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_add_account_bank;
    }

    @Override
    protected void initView(Bundle state) {
        validator = new Validator(this);
        validator.setValidationListener(this);

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        bName = bundle.getString(FormOtherFragment.BANK_NAME);
        bankName.setText(bName);

        dialog = new BottomSheetDialogGlobal().show(getFragmentManager(), BottomSheetDialogGlobal.HAVE_ACC_BANK,
                "Kepemilikan Rekening",
                "Apakah kamu memiliki nomor rekening pada "+bName,
                R.drawable.ic_bank);
        dialog.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {
                dialog.dismiss();
                Intent doc = new Intent(parentActivity(), AddDocumentActivity.class);
                parentActivity().finish();
                startActivity(doc);
            }

            @Override
            public void onClickButtonYes() {

                dialog.dismiss();
            }

            @Override
            public void closeApps() {
                //dont do anything in here
            }
        });

    }

    @OnClick(R.id.buttonNext)
    void onClick(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = new Bundle();
        bundle.putString(FormOtherFragment.ACC_NUMBER, accNumber.getText().toString());

        Intent doc = new Intent(parentActivity(), AddDocumentActivity.class);
        doc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        doc.putExtras(bundle);
        startActivity(doc);
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
