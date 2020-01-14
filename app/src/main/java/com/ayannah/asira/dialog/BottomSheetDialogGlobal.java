package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ImageUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetDialogGlobal extends BottomSheetDialogFragment {

    public static final String TITLE = "TITLE";
    public static final String DESCRRIPTION = "DESCRRIPTION";
    public static final String IMG = "IMG";
    public static final String BANKIMG = "BANKIMG";
    public static final String TYPE = "TYPE";

    //type instruction
    public static final String KTP_NPWP = "KTP_NPWP";
    public static final String HAVE_ACC_BANK = "HAVE_ACC_BANK";
    public static final String FORBIDDEN_LOAN_PNS = "FORBIDDEN_LOAN_PNS";
    public static final String RESEND_LOAN_FORBIDDEN = "RESEND_LOAN_FORBIDDEN";
    public static final String MAINTENANCE = "MAITENANCE";
    public static final String NO_ACCOUNT_NUMBER = "NO_ACCOUNT_NUMBER";
    public static final String NO_ACCOUNT_NUMBER_AGENT = "NO_ACCOUNT_NUMBER_AGENT";

    private BottomSheetInstructionListener listener;

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.imageInstruction)
    ImageView ivInsrtuction;

    @BindView(R.id.description)
    TextView desc;

    @BindView(R.id.buttonDialog)
    MaterialButton btnOk;


    @BindView(R.id.lyButton)
    LinearLayout lyButton;

    private String mode = null;

    public BottomSheetDialogGlobal() {
    }

    public BottomSheetDialogGlobal show(FragmentManager fragmentManager, String type, String title, String desc, int imgInstruction){

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(TYPE, type);
        args.putString(DESCRRIPTION, desc);
        args.putInt(IMG, imgInstruction);

        BottomSheetDialogGlobal fragment = new BottomSheetDialogGlobal();
        fragment.setArguments(args);
        fragment.show(fragmentManager, "TAG");

        return fragment;

    }

    public BottomSheetDialogGlobal showHaveBankAcc(FragmentManager fragmentManager, String type, String title, String desc, String img){

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(TYPE, type);
        args.putString(DESCRRIPTION, desc);
        args.putString(BANKIMG, img);

        BottomSheetDialogGlobal fragment = new BottomSheetDialogGlobal();
        fragment.setArguments(args);
        fragment.show(fragmentManager, "TAG");

        return fragment;

    }

    public void setOnClickBottomSheetInstruction(BottomSheetInstructionListener listener){
        this.listener = listener;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bottom_sheet_instruction, container, false);
        ButterKnife.bind(this, view);

        assert getArguments() != null;
        mode = getArguments().getString(TYPE);

        if (mode != null) {
            switch (mode) {

                case KTP_NPWP:

                    tvTitle.setText(getArguments().getString(TITLE));
                    ivInsrtuction.setImageResource(getArguments().getInt(IMG, 0));
                    desc.setText(getArguments().getString(DESCRRIPTION));
                    lyButton.setVisibility(View.GONE);

                    break;
                case HAVE_ACC_BANK:

                    ImageUtils.displayImageFromUrlWithErrorDrawable(view.getContext(), ivInsrtuction, getArguments().getString(BANKIMG), null);
                    tvTitle.setText(getArguments().getString(TITLE));
                    desc.setText(getArguments().getString(DESCRRIPTION));
                    btnOk.setVisibility(View.GONE);
                    lyButton.setVisibility(View.VISIBLE);

                    break;

                case RESEND_LOAN_FORBIDDEN:
                case FORBIDDEN_LOAN_PNS:

                    tvTitle.setText(getArguments().getString(TITLE));
                    ivInsrtuction.setImageResource(getArguments().getInt(IMG, 0));
                    desc.setText(getArguments().getString(DESCRRIPTION));
                    lyButton.setVisibility(View.GONE);
                    btnOk.setVisibility(View.VISIBLE);
                    btnOk.setOnClickListener(itemView -> {

                        listener.onClickButtonDismiss();

                    });

                    break;

                case MAINTENANCE:

                    tvTitle.setText(getArguments().getString(TITLE));
                    ivInsrtuction.setImageResource(getArguments().getInt(IMG, 0));
                    desc.setText(getArguments().getString(DESCRRIPTION));
                    lyButton.setVisibility(View.GONE);
                    btnOk.setVisibility(View.VISIBLE);
                    btnOk.setOnClickListener(itemView -> {

                        listener.closeApps();

                    });

                    break;

                case NO_ACCOUNT_NUMBER:
                    tvTitle.setText("Gagal mengajukan pinjaman");
                    ivInsrtuction.setImageResource(R.drawable.no_account_number);
                    desc.setText("Akun kamu belum memilki nomor rekening bank sehingga tidak dapat mengajukan pinjaman.");
                    lyButton.setVisibility(View.GONE);
                    btnOk.setVisibility(View.VISIBLE);
                    btnOk.setOnClickListener(itemView -> listener.closeApps());

                    break;

                case NO_ACCOUNT_NUMBER_AGENT:
                    tvTitle.setText("Nomor Rekening Nasabah Belum Tersedia");
                    ivInsrtuction.setImageResource(R.drawable.no_account_number);
                    desc.setText("Nasabah Anda belum bisa mengajukan pinjaman karena belum memiliki nomor rekening pada bank ini.");
                    lyButton.setVisibility(View.GONE);
                    btnOk.setVisibility(View.VISIBLE);
                    btnOk.setOnClickListener(itemView -> listener.closeApps());

                    break;

            }
        }


        return view;
    }

    @OnClick(R.id.buttonDialog)
    void onClickButtonDialog(){

        listener.onClickButtonDismiss();

    }

    @OnClick(R.id.btnYa)
    void onClickYa(){

        listener.onClickButtonYes();
    }

    @OnClick(R.id.btnBelum)
    void onClickBelum(){

        listener.onClickButtonDismiss();
    }

    public interface BottomSheetInstructionListener{

        void onClickButtonDismiss();

        void onClickButtonYes();

        void closeApps();
    }


}
