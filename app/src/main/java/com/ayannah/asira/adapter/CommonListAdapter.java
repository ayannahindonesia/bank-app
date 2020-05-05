package com.ayannah.asira.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.viewHolder_agent.AgentLoanVH;
import com.ayannah.asira.adapter.viewHolder_agent.AgentsClientVH;
import com.ayannah.asira.adapter.viewHolder_agent.DetilAngsuranVH;
import com.ayannah.asira.adapter.viewHolder_agent.QuestionVH;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.Angsuran;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.MenuAgent;
import com.ayannah.asira.data.model.Notif;
import com.ayannah.asira.data.model.Question;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_LOAN_HISTORY = 0;
    public static final int VIEW_NOTIFPAGE = 1;
    public static final int VIEW_BORROWER_ON_AGENT = 2;
    public static final int VIEW_LIST_BORROWERS_LOAN_AGENT = 3;
    public static final int VIEW_BANK_LIST = 4;
    public static final int VIEW_LIST_TOPUP_TAGIHAN = 5;
    public static final int VIEW_AGENT_MENU = 6;
    public static final int AGENT_VIEW_AGENTS_BORROWER = 7;
    public static final int AGENT_LIST_LOAN = 8;
    public static final int FAQ = 9;
    public static final int HOME_AGENT_PAGE = 10;
    public static final int VIEW_ALL_BORROWERS_AGENT = 11;
    public static final int VIEW_PAGING = 12;
    public static final int VIEW_DETIL_ANGSURAN = 13;

    private int selectedPage = 0;

    //for loan history purposes
    private final static String STATUS_PROCESSING = "processing";
    private final static String STATUS_APPROVED = "approved";
    private final static String STATUS_REJECTED = "rejected";

    private int mViewType;

    private List<Notif.Data> notifMessages;
    private List<DataItem> loans;
    private List<UserBorrower> nasabah;
    private List<DataItem> loanBorrowersAgent;
    private List<BankDetail> banks;
    private ArrayList<Integer> banksSelectedID;
    private ArrayList<Integer> banksSelectedIDServer;
    private List<String> menuTopupTagihan;
    private List<MenuAgent> menuAgents;
    private List<UserBorrower> agentsBorrowerList;
    private List<DataItem> loanInAgents;
    private List<Question.Data> questions;
    private List<Angsuran> angsurans;
    private ArrayList<InstallmentDetails> detilAngsurans;

    private CommonListListener.LoanAdapterListener loanListener;
    private CommonListListener.NotifAdapterListener notifListener;
    private CommonListListener.ViewBorrowerListener viewBorrowerListener;
    private CommonListListener.ListLoanAgent listLoanAgentListener;
    private CommonListListener.BankListListener bankListListener;
    private CommonListListener.CommonStringItemClickListener commonStringItemClickListener;
    private CommonListListener.MenuAgentListener menuAgentListener;
    private CommonListListener.AgentsClientListener agentsClientListener;
    private CommonListListener.QuestionListener questionListener;
    private CommonListListener.AngsuranListener angsuranListener;
    private CommonListListener.DetailAngsuranListener detailAngsuranListener;

    public CommonListAdapter(int viewType){
        this.mViewType = viewType;

        notifMessages = new ArrayList<>();
        loans = new ArrayList<>();
        nasabah = new ArrayList<>();
        loanBorrowersAgent = new ArrayList<>();
        banks = new ArrayList<>();
        banksSelectedID = new ArrayList<>();
        banksSelectedIDServer = new ArrayList<>();
        menuTopupTagihan = new ArrayList<>();
        menuAgents = new ArrayList<>();
        agentsBorrowerList = new ArrayList<>();
        loanInAgents = new ArrayList<>();
        questions = new ArrayList<>();
        angsurans = new ArrayList<>();
        detilAngsurans = new ArrayList<>();

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

    public void setListNasabah(List<UserBorrower> results){

        nasabah.clear();

        nasabah.addAll(results);

        notifyDataSetChanged();

    }

    public void setListLoanBorrowersAgent(List<DataItem> results){

        loanBorrowersAgent.clear();

        loanBorrowersAgent.addAll(results);

        notifyDataSetChanged();
    }

    public void setBanks(List<BankDetail> bankDetails, ArrayList<Integer> banksSelectedIDs, ArrayList<Integer> banksSelectedIDServers) {

        banks.clear();

        banks.addAll(bankDetails);

        banksSelectedID.addAll(banksSelectedIDs);

        banksSelectedIDServer.addAll(banksSelectedIDServers);

        notifyDataSetChanged();

    }

    public void setMenuTopupTagihan(List<String> results){

        menuTopupTagihan.clear();

        menuTopupTagihan.addAll(results);

        notifyDataSetChanged();
    }

    public void setMenuAgent(List<MenuAgent> results){

        menuAgents.clear();

        menuAgents.addAll(results);

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

    //view loan borrower on agent side
    public void setOnClickListenerLoanBorrowerOnAgent(CommonListListener.ListLoanAgent listLoanAgentListener){
        this.listLoanAgentListener = listLoanAgentListener;
    }

    //bank list listener
    public void setOnClickListenerBankList(CommonListListener.BankListListener bankListListener){
        this.bankListListener = bankListListener;
    }

    public void setOnClickMenuTopUpTaguhan(CommonListListener.CommonStringItemClickListener clickMenuTopUpTaguhan){
        this.commonStringItemClickListener = clickMenuTopUpTaguhan;
    }

    public void setOnClickMenuAgent(CommonListListener.MenuAgentListener menuAgentListener){
        this.menuAgentListener = menuAgentListener;
    }

    public void setAgentsBorrowerList(List<UserBorrower> results, int purpose){
        agentsBorrowerList.clear();

        if(purpose == HOME_AGENT_PAGE){

            if(results.size() > 5){

                //reverse add item to get latest borrower (DESC)
                for(int i=0; i < 5; i++){

                    int index = results.size();
                    agentsBorrowerList.add(results.get( index - 1 - i ));

                }

            }else {

                //reverse add item to get latest borrower (DESC)
                for(int i=0; i < results.size(); i++){

                    int index = results.size();
                    agentsBorrowerList.add(results.get( index - 1 - i ));

                }
            }

        }else {

            agentsBorrowerList.addAll(results);

        }

        notifyDataSetChanged();
    }

    public void setOnClickAgentsBorrowerListener(CommonListListener.AgentsClientListener listener){
        this.agentsClientListener = listener;
    }

    public void setListAgentLoan(List<DataItem> results){
        loanInAgents.clear();
        loanInAgents.addAll(results);
        notifyDataSetChanged();
    }

    public void setOnClickListenerLoanInAgent(CommonListListener.LoanAdapterListener listener){
        this.loanListener = listener;
    }

    public void setListQuestion(List<Question.Data> results){
        questions.clear();
        questions.addAll(results);
        notifyDataSetChanged();
    }
    public void setOnClickListenerQuestions(CommonListListener.QuestionListener listenerQuestions){
        this.questionListener = listenerQuestions;
    }

    public void setListAngsuran(List<Angsuran> results){
        angsurans.clear();
        angsurans.addAll(results);
        notifyDataSetChanged();
    }

    public void clearAngsuran(){
        angsurans.clear();
        detilAngsurans.clear();
    }

    public void setOnClickListenerAngsuran(CommonListListener.AngsuranListener angsuranListener){
        this.angsuranListener = angsuranListener;
    }

    public void setListDetilAngsuran(ArrayList<InstallmentDetails> results){
        detilAngsurans.clear();
        detilAngsurans.addAll(results);
        notifyDataSetChanged();
    }

    public void setOnClickListenerDetailAngsuran(CommonListListener.DetailAngsuranListener detailAngsuranListener){
        this.detailAngsuranListener = detailAngsuranListener;
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

            case VIEW_LIST_BORROWERS_LOAN_AGENT:

                holder = new ViewListLoanBorrowerOnAgent(inflater.inflate(R.layout.item_loan_borrower_on_agent, parent, false));
                break;

            case VIEW_BANK_LIST:

                holder = new BankListHolder(inflater.inflate(R.layout.item_banks, parent, false));
                break;

            case VIEW_LIST_TOPUP_TAGIHAN:

                holder = new ViewTopupTaguhanViwewHolder(inflater.inflate(R.layout.item_topup_tagihan, parent, false));
                break;

            case VIEW_AGENT_MENU:

                holder = new MenuAgentViewHolder(inflater.inflate(R.layout.item_menu_agent, parent, false));
                break;

            case AGENT_VIEW_AGENTS_BORROWER:

                holder = new AgentsClientVH(inflater.inflate(R.layout.item_agent_client, parent, false), agentsClientListener);
                break;

            case AGENT_LIST_LOAN:

                holder = new AgentLoanVH(inflater.inflate(R.layout.item_list_loan_agent, parent, false), loanListener);
                break;

            case FAQ:

                holder = new QuestionVH(inflater.inflate(R.layout.item_default_text, parent, false), questionListener);
                break;

            case VIEW_PAGING:

                holder = new AngsuranViewHolder(inflater.inflate(R.layout.item_paging, parent, false));
                break;

            case VIEW_DETIL_ANGSURAN:

                holder = new DetilAngsuranVH(inflater.inflate(R.layout.item_paging_detil_angsuran, parent, false), detailAngsuranListener);
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

            case VIEW_LIST_BORROWERS_LOAN_AGENT:

                ((ViewListLoanBorrowerOnAgent) holder).bind(loanBorrowersAgent.get(position));

                break;

            case VIEW_BANK_LIST:

                ((BankListHolder) holder).bind(banks.get(position));

                break;

            case VIEW_LIST_TOPUP_TAGIHAN:

                ((ViewTopupTaguhanViwewHolder) holder).bind(menuTopupTagihan.get(position));

                break;

            case VIEW_AGENT_MENU:

                ((MenuAgentViewHolder) holder).bind(menuAgents.get(position));

                break;

            case AGENT_VIEW_AGENTS_BORROWER:

                ((AgentsClientVH) holder).bind(agentsBorrowerList.get(position));
                break;

            case AGENT_LIST_LOAN:

                ((AgentLoanVH) holder).bind(loanInAgents.get(position));
                break;

            case FAQ:

                ((QuestionVH) holder).bind(questions.get(position));
                break;

            case VIEW_PAGING:

                ((AngsuranViewHolder) holder).bind(angsurans.get(position));
                break;

            case VIEW_DETIL_ANGSURAN:

                ((DetilAngsuranVH) holder).bind(detilAngsurans.get(position));
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

            case VIEW_LIST_BORROWERS_LOAN_AGENT:

                selected = VIEW_LIST_BORROWERS_LOAN_AGENT;
                break;

            case VIEW_BANK_LIST:

                selected = VIEW_BANK_LIST;
                break;

            case VIEW_LIST_TOPUP_TAGIHAN:

                selected = VIEW_LIST_TOPUP_TAGIHAN;
                break;

            case VIEW_AGENT_MENU:

                selected = VIEW_AGENT_MENU;
                break;

            case AGENT_VIEW_AGENTS_BORROWER:

                selected = AGENT_VIEW_AGENTS_BORROWER;
                break;

            case AGENT_LIST_LOAN:

                selected = AGENT_LIST_LOAN;
                break;

            case VIEW_PAGING:

                selected = VIEW_PAGING;
                break;

            case VIEW_DETIL_ANGSURAN:

                selected = VIEW_DETIL_ANGSURAN;
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

            case VIEW_LIST_BORROWERS_LOAN_AGENT:

                totals = loanBorrowersAgent.size();
                break;

            case VIEW_BANK_LIST:

                totals = banks.size();
                break;

            case VIEW_LIST_TOPUP_TAGIHAN:

                totals = menuTopupTagihan.size();
                break;

            case VIEW_AGENT_MENU:

                totals = menuAgents.size();
                break;

            case AGENT_VIEW_AGENTS_BORROWER:

                totals = agentsBorrowerList.size();
                break;

            case AGENT_LIST_LOAN:

                totals = loanInAgents.size();
                break;

            case FAQ:

                totals = questions.size();
                break;

            case VIEW_PAGING:

                totals = angsurans.size();
                break;

            case VIEW_DETIL_ANGSURAN:

                totals = detilAngsurans.size();
                break;
        }

        return totals;
    }

    /*
        For HistoryLoanFragment.class
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

            amount.setText(CommonUtils.setRupiahCurrency((int) Math.ceil(param.getLoanAmount())));

            typeLoan.setText(param.getProductName());

            numLoan.setText(String.valueOf(param.getId()));

            switch (param.getStatus().toLowerCase()){
                case STATUS_APPROVED:
                    status.setText(itemView.getResources().getString(R.string.accept));
                    status.setBackgroundResource(R.drawable.round_border);
                    status.setTextColor(itemView.getResources().getColor(R.color.textColorAsira));
                    break;
                case STATUS_PROCESSING:
                    status.setText(itemView.getResources().getString(R.string.processing));
                    status.setBackgroundResource(R.drawable.round_border_black);
                    status.setTextColor(itemView.getResources().getColor(R.color.textColorAsiraGrey));
                    break;
                case STATUS_REJECTED:
                    status.setText(itemView.getResources().getString(R.string.reject));
                    status.setBackgroundResource(R.drawable.round_border_red);
                    status.setTextColor(itemView.getResources().getColor(R.color.textColorAsiraRed));
                    break;
            }

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

        @BindView(R.id.photoUser) CircleImageView ivPhotoUser;

        @BindView(R.id.id_user) TextView idUser;

        @BindView(R.id.name_user) TextView nameUser;

        @BindView(R.id.status_user) TextView status;

        @BindView(R.id.btnAjukanPinjaman) LinearLayout btnAjukan;

        ViewBorrowerVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(UserBorrower param){

            ImageUtils.displayImageFromUrlWithErrorDrawable(itemView.getContext(), ivPhotoUser, param.getImage(), null);

            idUser.setText(String.valueOf(param.getId()));

            nameUser.setText(param.getFullname());

            status.setText(param.getLoanStatus());

            btnAjukan.setOnClickListener(v -> viewBorrowerListener.onClickButton(param));

            itemView.setOnClickListener(v -> viewBorrowerListener.onClick(param));

        }

    }

    /*
    For PengajuanFragment.class, PencairanFragment.class, DitolakFragment.class
     */
    class ViewListLoanBorrowerOnAgent extends RecyclerView.ViewHolder{

        @BindView(R.id.idLoan) TextView idLoan;
        @BindView(R.id.nameBorrower) TextView name;
        @BindView(R.id.loanTotalBorrower) TextView loanTotalBorrower;
        @BindView(R.id.tenorLoanBorrower) TextView tenorLoanBorrower;
        @BindView(R.id.productBorrower) TextView productBorrower;
        @BindView(R.id.statusLoanBorrower) TextView statusLoanBorrower;

        ViewListLoanBorrowerOnAgent(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(DataItem param){

            idLoan.setText(String.valueOf(param.getId()));
            name.setText(param.getBorrowerInfo().getFullname());
            loanTotalBorrower.setText(CommonUtils.setRupiahCurrency(param.getLoanAmount()));
            tenorLoanBorrower.setText(String.format("%s Bulan", param.getInstallment()));
            productBorrower.setText(param.getProductName());

            if(param.getStatus().equals("processing")){

                statusLoanBorrower.setBackgroundResource(R.drawable.badge_tidak_lengkap);
                statusLoanBorrower.setText("Dalam Proses");

            }else if(param.getStatus().equals("approved")){
                if (param.getDisburseStatus().equals("confirmed")) {
                    statusLoanBorrower.setBackgroundResource(R.drawable.badge_dicairkan);
                    statusLoanBorrower.setText("Sudah Dicairkan");
                } else {
                    statusLoanBorrower.setBackgroundResource(R.drawable.badge_diterima);
                    statusLoanBorrower.setText("Diterima");
                }

            }else {

                statusLoanBorrower.setText("Ditolak");
                statusLoanBorrower.setBackgroundResource(R.drawable.badge_ditolak);
            }

            itemView.setOnClickListener(v -> listLoanAgentListener.onClickItem(param));

        }
    }

    /*
        For AgentProfileBankListActivity.class
     */
    class BankListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.listLltBank) LinearLayout listLltBank;
//        @BindView(R.id.listImgBank) ImageView listImgBank;
        @BindView(R.id.listTxtBankName) TextView listTxtBankName;

        BankListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BankDetail bankDetail) {
//            listImgBank.setImageBitmap();
            listTxtBankName.setText(bankDetail.getName());

            for (int i=0; i<banksSelectedID.size(); i++) {
                if (String.valueOf(bankDetail.getId()).equals(banksSelectedID.get(i).toString())) {
                    listLltBank.setBackgroundResource(R.color.transdarkgreen);
                }
            }

            for (int i=0; i<banksSelectedIDServer.size(); i++) {
                if (String.valueOf(bankDetail.getId()).equals(banksSelectedIDServer.get(i).toString())) {
                    listLltBank.setEnabled(false);
                    listLltBank.setBackgroundResource(R.color.transdarkgreen);
                }
            }

            itemView.setOnClickListener(v ->
                    bankListListener.onClickItem(bankDetail, itemView, listLltBank)
            );
        }
    }

    class ViewTopupTaguhanViwewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.titleItem) TextView title;

        ViewTopupTaguhanViwewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void bind(String item){

            title.setText(item);

            itemView.setOnClickListener(v -> commonStringItemClickListener.onClickItem(item));

        }
    }

    /*
        To Menu Agent
     */
    class MenuAgentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.imgMenu)
        ImageView imgMenu;

        MenuAgentViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(MenuAgent param){

            title.setText(param.getName());

            imgMenu.setImageResource(param.getImg());

            itemView.setOnClickListener(v -> menuAgentListener.onClick(param));

        }
    }

    /*
        Angsuran
     */
    class AngsuranViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.lyNumber)
        LinearLayout lyNumber;

        @BindView(R.id.number)
        TextView tvNumber;

        AngsuranViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Angsuran data){

            if(getAdapterPosition() == selectedPage) {

                lyNumber.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                tvNumber.setTextColor(Color.WHITE);
                tvNumber.setText(data.getPage());

                angsuranListener.onClickAngsuran(data);

            }else {

                lyNumber.setBackgroundColor(Color.WHITE);
                tvNumber.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                tvNumber.setText(data.getPage());

            }

            itemView.setOnClickListener(v ->{

                selectedPage = getAdapterPosition();

                angsuranListener.onClickAngsuran(data);

                notifyDataSetChanged();

            });

        }
    }

}
