package com.ayannah.asira.adapter.viewHolder_agent;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentLoanVH extends RecyclerView.ViewHolder {

    @BindView(R.id.nameBorrower) TextView tvName;
    @BindView(R.id.status) TextView tvStatus;
    @BindView(R.id.amountLoanAndTenor) TextView tvAmountLoanAndTenor;
    @BindView(R.id.product) TextView tvProduct;
    @BindView(R.id.idLoan) TextView tvIdLoan;

    private CommonListListener.LoanAdapterListener loanAdapterListener;

    public AgentLoanVH(View itemView, CommonListListener.LoanAdapterListener loanAdapterListener){
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.loanAdapterListener = loanAdapterListener;
    }

    public void bind(DataItem param){

        switch (param.getStatus()) {

            case "rejected":

                tvName.setTextColor(itemView.getResources().getColor(R.color.customRed));
                tvAmountLoanAndTenor.setTextColor(itemView.getResources().getColor(R.color.customRed));
                tvStatus.setTextColor(itemView.getResources().getColor(R.color.customRed));
                tvStatus.setBackgroundResource(R.drawable.bg_status_reject);

                tvStatus.setText("Ditolak");
                break;

            case "approved":

                tvName.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                tvAmountLoanAndTenor.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                tvStatus.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                tvStatus.setBackgroundResource(R.drawable.bg_status_approve);

                tvStatus.setText("Diterima");
                break;

            default:

                tvName.setTextColor(itemView.getResources().getColor(R.color.customBlack));
                tvAmountLoanAndTenor.setTextColor(itemView.getResources().getColor(R.color.customBlack));
                tvStatus.setTextColor(itemView.getResources().getColor(R.color.customBlack));
                tvStatus.setBackgroundResource(R.drawable.bg_status_normal);

                tvStatus.setText("Dalam Proses");
                break;
        }

        tvName.setText(param.getBorrowerInfo().getFullname());

        tvAmountLoanAndTenor.setText(String.format("%s \u2022 %s bulan", CommonUtils.setRupiahCurrency(param.getLoanAmount()), param.getInstallment()));

        tvProduct.setText(param.getProductName());

        tvIdLoan.setText(String.format("ID %s", param.getId()));

        itemView.setOnClickListener(v -> loanAdapterListener.onClickItem(param));
    }
}
