package com.ayannah.asira.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.util.ImageUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetBorrowerAgent extends BottomSheetDialogFragment {

    @BindView(R.id.profilePict) ImageView ivProfilePict;
    @BindView(R.id.nama) TextView nameNasabah;
    @BindView(R.id.status) TextView statusNasabah;
    @BindView(R.id.bankName) TextView bankName;
    @BindView(R.id.norek) TextView noRekening;

    @BindView(R.id.id) TextView idNasabah;
    @BindView(R.id.pinjaman) TextView pinjaman;
    @BindView(R.id.nomorKtp) TextView tvKtp;
    @BindView(R.id.nomorHp) TextView nomorHp;
    @BindView(R.id.motherName) TextView tvMomsName;
    @BindView(R.id.pekerjaan) TextView pekerjaan;
    @BindView(R.id.alamat) TextView alamat;
    @BindView(R.id.root) LinearLayout linearLayout;

    private UserBorrower user;

    private BottomSheetBehavior mBehavior;

    public void setUserIdentity(UserBorrower user){
        this.user = user;
    }

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

        fillBorrowerIdentity(view);

        return dialog;
    }

    private void fillBorrowerIdentity(View view) {

        ImageUtils.displayImageFromUrlWithErrorDrawable(view.getContext(), ivProfilePict, user.getImage(), null);

        bankName.setText(user.getBankName());

        idNasabah.setText(String.valueOf(user.getId()));

        nameNasabah.setText(user.getFullname());

        alamat.setText(user.getAddress());

        tvKtp.setText(user.getIdcardNumber());

        tvMomsName.setText(user.getMotherName());

        statusNasabah.setText(user.getLoanStatus());

        noRekening.setText(user.getBankAccountnumber().isEmpty() ? "-" : user.getBankAccountnumber());

        pinjaman.setText(user.getNthLoans() == 0 ? "-" : String.valueOf(user.getNthLoans()));

        nomorHp.setText(user.getPhone());

        pekerjaan.setText(user.getOccupation());
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
