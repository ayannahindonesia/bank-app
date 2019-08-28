package com.ayannah.bantenbank.dialog;

import android.content.Intent;
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

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.screen.historyloan.HistoryLoanActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetDialogGlobal extends BottomSheetDialogFragment {

    public static final String TITLE = "TITLE";
    public static final String DESCRRIPTION = "DESCRRIPTION";
    public static final String IMG = "IMG";
    public static final String TYPE = "TYPE";

    //type instruction
    public static final String KTP_NPWP = "KTP_NPWP";
    public static final String HAVE_ACC_BANK = "HAVE_ACC_BANK";
    public static final String FORBIDDEN_LOAN_PNS = "FORBIDDEN_LOAN_PNS";
    public static final String RESEND_LOAN_FORBIDDEN = "RESEND_LOAN_FORBIDDEN";
    public static final String MAINTENANCE = "MAITENANCE";

    private BottomSheetInstructionListener listener;

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.imageInstruction)
    ImageView ivInsrtuction;

    @BindView(R.id.description)
    TextView desc;

    @BindView(R.id.buttonDialog)
    Button btnOk;


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

                    tvTitle.setText(getArguments().getString(TITLE));
                    ivInsrtuction.setImageResource(getArguments().getInt(IMG, 0));
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
