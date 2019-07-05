package com.ayannah.bantenbank.screen.register.addaccountbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.dialog.BottomSheetInstructionDialog;
import com.ayannah.bantenbank.screen.register.adddoc.AddDocumentActivity;
import com.ayannah.bantenbank.screen.register.adddoc.AddDocumentFragment;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAccountBankFragment extends BaseFragment implements AddAccountBankContract.View {

    private AddDocumentFragment fragmentadd = new AddDocumentFragment();

    BottomSheetInstructionDialog dialog;

    @BindView(R.id.bankName)
    TextView bankName;
    String bName = null;

    @BindView(R.id.regist_accNumber)
    EditText accNumber;

    @Inject
    public AddAccountBankFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_add_account_bank;
    }

    @Override
    protected void initView(Bundle state) {

        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        bName = bundle.getString(FormOtherFragment.BANK_NAME);
        bankName.setText(bName);

        dialog = new BottomSheetInstructionDialog().show(getFragmentManager(), BottomSheetInstructionDialog.HAVE_ACC_BANK,
                "Kepemilikan Rekening",
                "Apakah kamu memiliki nomor rekening pada "+bName,
                R.drawable.ic_bank);
        dialog.setOnClickBottomSheetInstruction(new BottomSheetInstructionDialog.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {
                Intent doc = new Intent(parentActivity(), AddDocumentActivity.class);
                doc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(doc);

                dialog.dismiss();
            }

            @Override
            public void onClickButtonYes() {

                dialog.dismiss();
            }
        });

    }

    @OnClick(R.id.buttonNext)
    void onClick(){
        Bundle bundle = new Bundle();
        bundle.putString(FormOtherFragment.ACC_NUMBER, accNumber.getText().toString());

        Intent doc = new Intent(parentActivity(), AddDocumentActivity.class);
        doc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        doc.putExtras(bundle);
        startActivity(doc);

    }
}
