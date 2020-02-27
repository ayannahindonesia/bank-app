package com.ayannah.asira.screen.register.addaccountbank;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.dialog.BottomSheetAggreement;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.register.adddoc.AddDocumentActivity;
import com.ayannah.asira.screen.register.adddoc.AddDocumentFragment;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.util.ImageUtils;
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

    @BindView(R.id.bankName) TextView bankName;
    String bName = null;

//    @BindView(R.id.bankImage)
//    ImageView bankImage;

    @NotEmpty(message = "Masukan nomor rekening")
    @BindView(R.id.regist_accNumber)
    EditText accNumber;

    private Validator validator;

    @Inject
    public AddAccountBankFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_add_account_bank_new;
    }

    @Override
    protected void initView(Bundle state) {
        validator = new Validator(this);
        validator.setValidationListener(this);

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;

//        ImageUtils.displayImageFromUrlWithErrorDrawable(parentActivity(), bankImage, bundle.getString(FormOtherFragment.BANK_LOGO), null);

        bName = bundle.getString(FormOtherFragment.BANK_NAME);
        bankName.setText(bName);

    }

    @OnClick(R.id.buttonNext)
    void onClick(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = parentActivity().getIntent().getExtras();
        bundle.putString(FormOtherFragment.ACC_NUMBER, accNumber.getText().toString());

        Intent doc = new Intent(parentActivity(), AddDocumentActivity.class);
        doc.putExtras(bundle);
        doc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    @OnClick(R.id.accountDisclaimer)
    void showPopUp() {
        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;

        dialog = new BottomSheetDialogGlobal().showHaveBankAcc(getFragmentManager(), BottomSheetDialogGlobal.HAVE_ACC_BANK,
                "Persetujuan permbuatan rekening",
                "Apakah anda bersedia jika data yang dimasukkan akan digunakan juga sebagai data pengajuan rekening baru di "+bName+"?");

        dialog.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {

                dialog.dismiss();
            }

            @Override
            public void onClickButtonYes() {
                Bundle bundle1 = parentActivity().getIntent().getExtras();
                Intent doc = new Intent(parentActivity(), AddDocumentActivity.class);
                doc.putExtras(bundle1);
                startActivity(doc);
            }

            @Override
            public void closeApps() {
                //dont do anything in here
            }
        });
    }
}
