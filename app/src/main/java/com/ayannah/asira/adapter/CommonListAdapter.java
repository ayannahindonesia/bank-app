package com.ayannah.asira.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.local.BankServiceLocal;
import com.ayannah.asira.data.local.ServiceProductLocal;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.model.Notif;
import com.ayannah.asira.data.model.UserProfile;
import com.ayannah.asira.util.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_LOAN_HISTORY = 0;
    public static final int VIEW_NOTIFPAGE = 1;
    public static final int VIEW_BORROWER_ON_AGENT = 2;

    //for loan history purposes
    private final static String STATUS_PROCESSING = "processing";
    private final static String STATUS_APPROVED = "approved";
    private final static String STATUS_REJECTED = "rejected";

    private int mViewType;

    private List<Notif.Data> notifMessages;
    private List<DataItem> loans;
    private List<UserProfile> nasabah;

    private CommonListListener.LoanAdapterListener loanListener;
    private CommonListListener.NotifAdapterListener notifListener;
    private CommonListListener.ViewBorrowerListener viewBorrowerListener;

    public CommonListAdapter(int viewType){
        this.mViewType = viewType;

        notifMessages = new ArrayList<>();
        loans = new ArrayList<>();
        nasabah = new ArrayList<>();
    }

    public void setDataNotificationMessages(List<Notif.Data> results){

        notifMessages.clear();

        notifMessages.addAll(results);

        notifyDataSetChanged();
    }

    public void setDateLoans(List<DataItem> resutls){

        loans.clear();

        loans.addAll(resutls);

        notifyDataSetChanged();
    }

    public void setListNasabah(List<UserProfile> results){

        nasabah.clear();

        nasabah.addAll(results);

        notifyDataSetChanged();

    }

    //loan listener
    public void setOnClickListenerLoanAdapter(CommonListListener.LoanAdapterListener listenerLoanAdapter){
        this.loanListener = listenerLoanAdapter;
    }

    //noitf listener
    public void setOnClickListnerNotifAdapter(CommonListListener.NotifAdapterListener listnerNotifAdapter){
        this.notifListener = listnerNotifAdapter;
    }

    //view borrower listener
    public void setOnClickListenerViewBorrower(CommonListListener.ViewBorrowerListener listenerViewBorrower){
        this.viewBorrowerListener = listenerViewBorrower;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecyclerView.ViewHolder holder;

        switch (mViewType){

            case VIEW_NOTIFPAGE:

                holder = new NotifListVH(inflater.inflate(R.layout.item_notif, parent, false));
                break;

            case VIEW_LOAN_HISTORY:

                holder = new LoanHistoryVH(inflater.inflate(R.layout.item_my_loan, parent, false));
                break;

            case VIEW_BORROWER_ON_AGENT:

                holder = new ViewBorrowerVH(inflater.inflate(R.layout.item_nasabah, parent, false));
                break;

            default:

                holder = null;
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (mViewType){

            case VIEW_NOTIFPAGE:

                ((NotifListVH) holder).bind(notifMessages.get(position));

                break;

            case VIEW_LOAN_HISTORY:

                ((LoanHistoryVH) holder).bind(loans.get(position));

                break;

            case VIEW_BORROWER_ON_AGENT:

                ((ViewBorrowerVH) holder).bind(nasabah.get(position));

                break;
        }

    }

    @Override
    public int getItemViewType(int position) {

        int selected = 0;

        switch (position){

            case VIEW_NOTIFPAGE:

                selected = VIEW_NOTIFPAGE;
                break;

            case VIEW_LOAN_HISTORY:

                selected = VIEW_LOAN_HISTORY;
                break;

            case VIEW_BORROWER_ON_AGENT:

                selected = VIEW_BORROWER_ON_AGENT;
                break;
        }

        return selected;
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

            case VIEW_BORROWER_ON_AGENT:

                totals = nasabah.size();
                break;

        }

        return totals;
    }

    /*
        For DetailTransaksiActivity.class
     */
    class LoanHistoryVH extends RecyclerView.ViewHolder{

        @BindView(R.id.numLoan)
        TextView numLoan;

        @BindView(R.id.typeLoan)
        TextView typeLoan;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.amount)
        TextView amount;

        LoanHistoryVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void bind(DataItem param){

//            JSONObject jsonObject = null;
//            String serviceNya = "";
//            String productNya = "";
//
//            BankServiceLocal bankServiceLocal = new BankServiceLocal(itemView.getContext());
//            ServiceProductLocal serviceProductLocal = new ServiceProductLocal(itemView.getContext());
//
//            try {
//
//                JSONArray jsonArray = new JSONArray(bankServiceLocal.getBankService());
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject1 = new JSONObject(String.valueOf(jsonArray.get(i)));
//                    if (param.getService().equals(jsonObject1.get("id").toString())) {
//                        serviceNya = jsonObject1.get("name").toString();
//                    }
//                }
//
//                JSONArray jsonArray1 = new JSONArray(serviceProductLocal.getServiceProducts());
//                for (int j = 0; j < jsonArray1.length(); j++) {
//                    JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonArray1.get(j)));
//                    if (param.getProduct().equals(jsonObject2.get("id").toString())) {
//                        productNya = jsonObject2.get("name").toString();
//                    }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            numLoan.setText(param.getService() + " - " + param.getId());
//            numLoan.setText(String.format("%s - ", param.getService()));
//            numLoan.setText(String.format("%1$s - %2$s", param.getService(), param.getId()));
            numLoan.setText(String.format("%1$s - %2$s", param.getServiceName(), param.getId()));

//            typeLoan.setText(param.getService());
            typeLoan.setText(param.getProductName());

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

            itemView.setOnClickListener(v -> loanListener.onClickItem(param));
        }

    }

    /*
        For NotifPageActivity.class
     */
    class NotifListVH extends RecyclerView.ViewHolder{

        @BindView(R.id.txtMessage)
        TextView txtMessage;

        NotifListVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Notif.Data param){

            txtMessage.setText(param.getMessage_body());

//            itemView.setOnClickListener(v -> {
//
//                notifListener.onClickItem(param);
//            });

        }

    }

    /*
        For ViewBorrowerActivity.class
     */
    class ViewBorrowerVH extends RecyclerView.ViewHolder{

        @BindView(R.id.photoUser) ImageView ivPhotoUser;

        @BindView(R.id.id_user) TextView idUser;

        @BindView(R.id.name_user) TextView nameUser;

        @BindView(R.id.status_user) TextView status;

        @BindView(R.id.btnAjukanPinjaman) Button btnAjukan;

        ViewBorrowerVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(UserProfile param){

            idUser.setText(String.valueOf(param.getId()));

            nameUser.setText(param.getEmployerName());

            status.setText("Aktif");

            btnAjukan.setOnClickListener(v -> viewBorrowerListener.onClickButton());

            itemView.setOnClickListener(v -> viewBorrowerListener.onClick());

        }

    }

}
