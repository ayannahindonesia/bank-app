package com.ayannah.bantenbank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.Loans;
import com.ayannah.bantenbank.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoadViewHolder> {

    private Context mContext;
    private List<Loans> loans;
    private LoansAdapterListener listener;

    public LoanAdapter(Context mContext){
        this.mContext = mContext;

        loans = new ArrayList<>();
    }

    public void setLoanListener(LoansAdapterListener listener){
        this.listener = listener;
    }

    public void setLoanData(List<Loans> results){
        loans.clear();

        loans = results;

        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public LoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_loan, parent, false);
        return new LoadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadViewHolder holder, int position) {

        holder.bind(loans.get(position));
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    class LoadViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.numLoan)
        TextView numLoan;

        @BindView(R.id.typeLoan)
                TextView typeLoan;

        @BindView(R.id.status)
                TextView status;

        @BindView(R.id.amount)
                TextView amount;

        LoadViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Loans param){

            numLoan.setText(String.valueOf(param.getNoLoan()));

            typeLoan.setText(param.getLoanType());

            switch (param.getStatus()){
                case "DITERIMA":
                    status.setTextColor(Color.GREEN);
                    break;
                case "TIDAK LENGKAP":
                    status.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    break;
            }
            status.setText(param.getStatus());

            amount.setText(CommonUtils.setRupiahCurrency(param.getAmount()));

            itemView.setOnClickListener(view -> listener.onClickItem(param));
        }
    }

    public interface LoansAdapterListener{
        void onClickItem(Loans loans);
    }

}
