package com.ayannah.asira.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.BankTypeDummy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NestedSelectBankAdapter extends RecyclerView.Adapter<NestedSelectBankAdapter.ViewParentBank> {

    private List<BankTypeDummy> bankTypeDummies;

    public NestedSelectBankAdapter(){

        bankTypeDummies = new ArrayList<>();

    }

    public void setDataBankType(List<BankTypeDummy> dummies){

        bankTypeDummies.clear();

        bankTypeDummies.addAll(dummies);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewParentBank onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_bank_type, parent, false);
        return new ViewParentBank(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewParentBank holder, int position) {

        holder.bind(bankTypeDummies.get(position));

    }

    @Override
    public int getItemCount() {
        return bankTypeDummies.size();
    }

    class ViewParentBank extends RecyclerView.ViewHolder{

        @BindView(R.id.recyclerViewBanks)
        RecyclerView recyclerViewBanks;

        @BindView(R.id.jenisBank)
        TextView jenisBank;

        @BindView(R.id.collapseBtn)
        ImageButton collapseBtn;

        boolean isShow;

        ViewParentBank(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void bind(BankTypeDummy x){

            jenisBank.setText(x.getType());

            DummyChildBankAdapter adapterChild = new DummyChildBankAdapter(x.getBanks());
            recyclerViewBanks.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            recyclerViewBanks.setHasFixedSize(true);
            recyclerViewBanks.setAdapter(adapterChild);

            collapseBtn.setOnClickListener(v -> {

                if(!isShow){

                    recyclerViewBanks.setVisibility(View.VISIBLE);
                    isShow = true;

                }else {

                    recyclerViewBanks.setVisibility(View.GONE);
                    isShow = false;
                }
            });

        }
    }
}
