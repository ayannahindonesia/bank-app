package com.ayannah.asira.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.BankDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseBankAdapter extends RecyclerView.Adapter<ChooseBankAdapter.ChooseBankVH> {

    Application application;
    List<BankDetail> listBanks;
    ChooseBankListener listener;

    public ChooseBankAdapter(Application application){
        this.application = application;

        listBanks = new ArrayList<>();
    }

    public void setOnClickBankListener(ChooseBankListener listener){
        this.listener = listener;
    }

    public void setItemBank(List<BankDetail> results){
        listBanks.clear();

        listBanks.addAll(results);

        notifyDataSetChanged();
    }

    public interface ChooseBankListener{
        void onClickItemBank(BankDetail bank);
    }

    @NonNull
    @Override
    public ChooseBankVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_bank, parent, false);

        return new ChooseBankVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseBankVH holder, int position) {

        holder.binf(listBanks.get(position));
    }

    @Override
    public int getItemCount() {
        return listBanks.size();
    }

    class ChooseBankVH extends RecyclerView.ViewHolder{

        @BindView(R.id.tvNameBank)
        TextView nameBank;

        ChooseBankVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void binf(BankDetail bank){

            nameBank.setText(bank.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickItemBank(bank);
                }
            });
        }

    }

}
