package com.ayannah.bantenbank.screen.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAccountBank extends Fragment {

    private AddDocumentRegister fragmentadd = new AddDocumentRegister();

    public static final String BANK_NAME = "BANK_NAME";

    @BindView(R.id.bankName)
    TextView bankName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_account_bank, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        bankName.setText(bundle.getString(BANK_NAME));

        return view;
    }

    @OnClick(R.id.buttonNext)
    void onClick(){

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragmentadd);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }
}
