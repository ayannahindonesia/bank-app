package com.ayannah.bantenbank.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ayannah.bantenbank.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetInstructionDialog extends BottomSheetDialogFragment {

    public static final String TITLE = "TITLE";
    public static final String DESCRRIPTION = "DESCRRIPTION";
    public static final String IMG = "IMG";

    private BottomSheetInstructionListener listener;

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.imageInstruction)
    ImageView ivInsrtuction;

    @BindView(R.id.description)
    TextView desc;

    @BindView(R.id.buttonDialog)
    Button btnOk;

    public BottomSheetInstructionDialog show(FragmentManager fragmentManager, String title, String desc, int imgInstruction){

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESCRRIPTION, desc);
        args.putInt(IMG, imgInstruction);

        BottomSheetInstructionDialog fragment = new BottomSheetInstructionDialog();
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

        tvTitle.setText(getArguments().getString(TITLE));
        ivInsrtuction.setImageResource(getArguments().getInt(IMG, 0));
        desc.setText(getArguments().getString(DESCRRIPTION));

        return view;
    }

    @OnClick(R.id.buttonDialog)
    void onClickButtonDialog(){

        listener.onClickButtonDismiss();

    }

    public interface BottomSheetInstructionListener{

        void onClickButtonDismiss();
    }


}
