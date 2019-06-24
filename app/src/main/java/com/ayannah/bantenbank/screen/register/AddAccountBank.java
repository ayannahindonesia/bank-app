package com.ayannah.bantenbank.screen.register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.UserRegister;
import com.ayannah.bantenbank.dialog.BottomSheetInstructionDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAccountBank extends Fragment {

    private AddDocumentRegister fragmentadd = new AddDocumentRegister();

    public static final String BANK_NAME = "BANK_NAME";

    BottomSheetInstructionDialog dialog;

    @BindView(R.id.bankName)
    TextView bankName;
    String bName = null;

    @BindView(R.id.regist_accNumber)
    EditText accNumber;

    RegisterListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (RegisterListener) context;
    }

    private void onDataPass(UserRegister data){
        listener.onDataPass(data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account_bank, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        assert bundle != null;
        bName = bundle.getString(BANK_NAME);
        bankName.setText(bName);

        dialog = new BottomSheetInstructionDialog().show(getFragmentManager(), BottomSheetInstructionDialog.HAVE_ACC_BANK,
                "Kepemilikan Rekening",
                "Apakah kamu memiliki nomor rekening pada "+bName,
                R.drawable.ic_bank);
        dialog.setOnClickBottomSheetInstruction(new BottomSheetInstructionDialog.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragmentadd);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

                dialog.dismiss();
            }

            @Override
            public void onClickButtonYes() {

                dialog.dismiss();
            }
        });



        return view;
    }

    @OnClick(R.id.buttonNext)
    void onClick(){

        UserRegister param = new UserRegister();
        param.setBankAccountnumber(accNumber.getText().toString().trim());

        listener.onDataPass(param);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragmentadd);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }
}
