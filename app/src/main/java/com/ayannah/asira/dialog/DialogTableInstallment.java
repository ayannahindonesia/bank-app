package com.ayannah.asira.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.InstallmentTableAdapter;
import com.ayannah.asira.data.model.Installments;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogTableInstallment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private ArrayList<Installments> installments;
    private InstallmentTableAdapter installmentTableAdapter;

    @BindView(R.id.ll)
    LinearLayout ll;

    @BindView(R.id.rvTableInstallment)
    RecyclerView rvTableInstallment;

    public void setInstallments(ArrayList<Installments> result) {
        installments = result;
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.dialog_table_installment, null);
        ButterKnife.bind(this, view);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());

        installmentTableAdapter = new InstallmentTableAdapter(getActivity().getApplication());
        installmentTableAdapter.setInstallmentTable(installments);

        rvTableInstallment.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTableInstallment.setHasFixedSize(true);
        rvTableInstallment.setAdapter(installmentTableAdapter);

        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
