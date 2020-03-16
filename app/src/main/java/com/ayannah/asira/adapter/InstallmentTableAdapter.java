package com.ayannah.asira.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.Installments;
import com.ayannah.asira.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstallmentTableAdapter extends RecyclerView.Adapter<InstallmentTableAdapter.InstallmentAdapterVH> {

    private Application application;
    private List<Installments> listData;

    public InstallmentTableAdapter(Application application){
        this.application = application;

        listData = new ArrayList<>();
    }

    public void setInstallmentTable(List<Installments> results){
        listData.clear();

        listData.addAll(results);

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public InstallmentTableAdapter.InstallmentAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_installment, parent, false);

        return new InstallmentAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentTableAdapter.InstallmentAdapterVH holder, int position) {
        holder.bind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class InstallmentAdapterVH extends RecyclerView.ViewHolder {

        @BindView(R.id.index)
        TextView index;

        @BindView(R.id.angsuranPerBulan)
        TextView angsuranPerBulan;

        public InstallmentAdapterVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Installments installments) {
            index.setText(String.valueOf(installments.getIndex()));
            angsuranPerBulan.setText(CommonUtils.setRupiahCurrency((int) Double.parseDouble(installments.getAngsuranPerBulan())));
        }
    }
}
