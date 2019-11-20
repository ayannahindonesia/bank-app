package com.ayannah.asira.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ayannah.asira.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetBorrowerAgent extends BottomSheetDialogFragment {

    @BindView(R.id.id) TextView idNasabah;
    @BindView(R.id.nama) TextView nameNasabah;
    @BindView(R.id.status) TextView statusNasabah;
    @BindView(R.id.norek) TextView noRekening;
    @BindView(R.id.pinjaman) TextView pinjaman;
    @BindView(R.id.nomorHp) TextView nomorHp;
    @BindView(R.id.statusPernikahan) TextView statusPernikahan;
    @BindView(R.id.namaPasangan) TextView namaPasangan;
    @BindView(R.id.pekerjaan) TextView pekerjaan;
    @BindView(R.id.root) LinearLayout linearLayout;


    private BottomSheetBehavior mBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.bottom_sheet_borrower_agent, null);
        ButterKnife.bind(this, view);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = getScreenHeight();
        linearLayout.setLayoutParams(params);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getScreenHeight(){

        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
