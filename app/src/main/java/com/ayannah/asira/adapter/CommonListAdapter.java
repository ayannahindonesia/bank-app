package com.ayannah.asira.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.Loans.DataItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_LOAN_HISTORY = 0;
    public static final int VIEW_NOTIFPAGE = 1;

    private int mViewType;

    private List<String> notifMessages;
    private List<DataItem> loans;

    public CommonListAdapter(int viewType){
        this.mViewType = viewType;

        notifMessages = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public void setDataNotificationMessages(List<String> results){

        notifMessages.clear();

        notifMessages.addAll(results);

        notifyDataSetChanged();
    }

    public void setDateLoans(List<DataItem> resutls){

        loans.clear();

        loans.addAll(resutls);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecyclerView.ViewHolder holder;

        viewType = mViewType;

        switch (viewType){

            case VIEW_NOTIFPAGE:
                holder = new NotifListVH(inflater.inflate(R.layout.item_notif, parent, false));
                break;

            case VIEW_LOAN_HISTORY:
                holder = new LoanHistoryVH(inflater.inflate(R.layout.item_my_loan, parent, false));
                break;

            default:
                holder = null;
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){

            case VIEW_NOTIFPAGE:

                ((NotifListVH) holder).bind(notifMessages.get(position));

                break;

            case VIEW_LOAN_HISTORY:

                ((LoanHistoryVH) holder).bind();

                break;
        }

    }

    @Override
    public int getItemCount() {

        int totals = 0;

        switch (mViewType){

            case VIEW_NOTIFPAGE:

                totals =  notifMessages.size();
                break;

            case VIEW_LOAN_HISTORY:

                totals = loans.size();
                break;

        }

        return totals;
    }

    /*
        For DetailTransaksiActivity.class
     */
    public class LoanHistoryVH extends RecyclerView.ViewHolder{

        LoanHistoryVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void bind(){

        }

    }

    /*
        For NotifPageActivity.class
     */
    public class NotifListVH extends RecyclerView.ViewHolder{

        @BindView(R.id.txtMessage)
        TextView txtMessage;

        NotifListVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(String param){

            txtMessage.setText(param);

        }

    }
}
