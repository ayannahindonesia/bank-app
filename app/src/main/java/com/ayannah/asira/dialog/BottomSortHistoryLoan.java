package com.ayannah.asira.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ayannah.asira.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BottomSortHistoryLoan extends BottomSheetDialogFragment {

    @BindView(R.id.processing)
    TextView processing;

    @BindView(R.id.rejected)
    TextView rejected;

    @BindView(R.id.accepted)
    TextView accepted;

    private Unbinder mUnbinder;

    private SortHistoryLoanListener listener;

    public BottomSortHistoryLoan show(AppCompatActivity appCompatActivity){

        BottomSortHistoryLoan fragment = new BottomSortHistoryLoan();
        fragment.showNow(appCompatActivity.getSupportFragmentManager(), "filter");

        return fragment;
    }

    public void setOnClickListener(SortHistoryLoanListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_sort_loan, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        processing.setOnClickListener(v -> listener.onClickStatus("processing"));

        rejected.setOnClickListener(v -> listener.onClickStatus("rejected"));

        accepted.setOnClickListener(v -> listener.onClickStatus("approved"));

        return view;
    }

    public interface SortHistoryLoanListener{

        void onClickStatus(String status);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
