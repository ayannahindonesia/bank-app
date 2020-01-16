package com.ayannah.asira.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.util.ImageUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomSheetAggreement extends BottomSheetDialogFragment {

    private Unbinder mUnbinder;
    private BottomSheetKebijakanListener listener;

    @BindView(R.id.imageInstruction)
    ImageView imageBank;

    @BindView(R.id.desc)
    TextView desc;

    @BindView(R.id.title)
    TextView title;

    private String bankName;
    private String logoImage;

    public void setOnCheckListener(BottomSheetKebijakanListener listener){
        this.listener = listener;
    }

    public void setBankName(String result, String logo){
        this.bankName = result;
        this.logoImage = logo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_aggreement, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        ImageUtils.displayImageFromUrlWithErrorDrawable(view.getContext(), imageBank, logoImage, null);

        String first = "Apakah anda bersedia jika data yang dimasukkan akan digunakan juga sebagai ";
        String boldText = "data pengajuan rekening baru";
        String bank = " di "+ bankName + "?";

        //bold text
        SpannableString span = new SpannableString(first + boldText + bank);
        span.setSpan(new StyleSpan(Typeface.BOLD), first.length(), first.length() + boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        desc.setText(span);

        return view;
    }

    @OnClick(R.id.btn_confirm)
    void onClick(){

        listener.onClickSetuju();

    }

//    @OnClick(R.id.close)
//    void onClickClose(){
//
//        listener.onClickClose();
//
//    }

    public interface BottomSheetKebijakanListener{

        void onClickSetuju();

//        void onClickClose();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
