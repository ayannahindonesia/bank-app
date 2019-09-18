package com.ayannah.asira.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.local.BankServiceLocal;
import com.ayannah.asira.data.local.ServiceProductLocal;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.util.CommonUtils;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoadViewHolder> {

    private final static String STATUS_PROCESSING = "processing";
    private final static String STATUS_APPROVED = "approved";
    private final static String STATUS_REJECTED = "rejected";

    private Context mContext;
    private List<DataItem> loans;
    private LoansAdapterListener listener;

    public LoanAdapter(Context mContext){
        this.mContext = mContext;

        loans = new ArrayList<>();
    }

    public void setLoanListener(LoansAdapterListener listener){
        this.listener = listener;
    }

    public void setLoanData(List<DataItem> results){
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

        private void bind(DataItem param){
            JSONObject jsonObject = null;
            String serviceNya = "";
            String productNya = "";

            BankServiceLocal bankServiceLocal = new BankServiceLocal(mContext);
            ServiceProductLocal serviceProductLocal = new ServiceProductLocal(mContext);

            try {

                JSONArray jsonArray = new JSONArray(bankServiceLocal.getBankService());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = new JSONObject(String.valueOf(jsonArray.get(i)));
                    if (param.getService().equals(jsonObject1.get("id").toString())) {
                        serviceNya = jsonObject1.get("name").toString();
                    }
                }

                JSONArray jsonArray1 = new JSONArray(serviceProductLocal.getServiceProducts());
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonArray1.get(j)));
                    if (param.getService().equals(jsonObject2.get("id").toString())) {
                        productNya = jsonObject2.get("name").toString();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            numLoan.setText(param.getService() + " - " + param.getId());
//            numLoan.setText(String.format("%s - ", param.getService()));
//            numLoan.setText(String.format("%1$s - %2$s", param.getService(), param.getId()));
            numLoan.setText(String.format("%1$s - %2$s", serviceNya, param.getId()));

//            typeLoan.setText(param.getService());
            typeLoan.setText(productNya);

//            if(param.isOtpVerified()){
//
//                switch (param.getStatus().toLowerCase()){
//                    case STATUS_ACCEPTED:
//                        status.setText(itemView.getResources().getString(R.string.accept));
//                        status.setBackgroundResource(R.drawable.badge_diterima);
//                        break;
//                    case STATUS_PROCESSING:
//                        status.setText(itemView.getResources().getString(R.string.processing));
//                        status.setBackgroundResource(R.drawable.badge_tidak_lengkap);
//                        break;
//                    case STATUS_REJECTED:
//                        status.setText(itemView.getResources().getString(R.string.reject));
//                        status.setBackgroundResource(R.drawable.badge_ditolak);
//                        break;
//                }
//
//            }else {
//
//                status.setBackgroundResource(R.drawable.badge_tertunda);
//                status.setText("Tertunda");
//            }

            switch (param.getStatus().toLowerCase()){
                case STATUS_APPROVED:
                    status.setText(itemView.getResources().getString(R.string.accept));
                    status.setBackgroundResource(R.drawable.badge_diterima);
                    break;
                case STATUS_PROCESSING:
                    status.setText(itemView.getResources().getString(R.string.processing));
                    status.setBackgroundResource(R.drawable.badge_tidak_lengkap);
                    break;
                case STATUS_REJECTED:
                    status.setText(itemView.getResources().getString(R.string.reject));
                    status.setBackgroundResource(R.drawable.badge_ditolak);
                    break;
            }

            amount.setText(CommonUtils.setRupiahCurrency(param.getLoanAmount()));

            itemView.setOnClickListener(view -> listener.onClickItem(param));
        }
    }

    public interface LoansAdapterListener{
        void onClickItem(DataItem loans);
    }

}
