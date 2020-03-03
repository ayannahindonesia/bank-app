package com.ayannah.asira.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.BankService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceDescriptionAdapter extends RecyclerView.Adapter<ServiceDescriptionAdapter.ServicesDescVH> {


    Application application;
    List<BankService.Data> listData;

    public ServiceDescriptionAdapter(Application application){
        this.application = application;

        listData = new ArrayList<>();
    }

    public void setServiceDesc(List<BankService.Data> results){
        listData.clear();

        listData.addAll(results);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceDescriptionAdapter.ServicesDescVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_service_desc, parent, false);

        return new ServicesDescVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceDescriptionAdapter.ServicesDescVH holder, int position) {
        holder.bind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ServicesDescVH extends RecyclerView.ViewHolder{

        @BindView(R.id.service)
        TextView service;

        @BindView(R.id.description)
        TextView description;

        ServicesDescVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BankService.Data data){

            service.setText(data.getName());
            description.setText(data.getDescription());
        }

    }
}
