package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ayannah.asira.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomSheetKebijakan extends BottomSheetDialogFragment {

    private Unbinder mUnbinder;
    private BottomSheetKebijakanListener listener;

    @BindView(R.id.checkBox)
    CheckBox checkBox;

    public void setOnCheckListener(BottomSheetKebijakanListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_kebijakan_privacy, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                listener.onCheckSetuju(isChecked);

            }
        });

        return view;
    }

    public interface BottomSheetKebijakanListener{

        void onCheckSetuju(boolean isChecked);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
