package com.ayannah.asira.adapter.viewHolder_agent;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetilAngsuranVH extends RecyclerView.ViewHolder {

    @BindView(R.id.total)
    TextView tvTotal;

    @BindView(R.id.status)
    TextView tvStatus;

    @BindView(R.id.tenor)
    TextView tvTenor;

    @BindView(R.id.datetime)
    TextView tvDatetime;

    private CommonListListener.DetailAngsuranListener listener;

    public DetilAngsuranVH(View itemView, CommonListListener.DetailAngsuranListener listener){
        super(itemView);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(InstallmentDetails param){

        tvTotal.setText(CommonUtils.setRupiahCurrency(param.getLoanPayment()));

        if(param.isPaidStatus()){
            tvStatus.setBackgroundResource(R.drawable.bg_status_approve);
            tvStatus.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
            tvStatus.setText("Telah dibayar");
        }else {
            tvStatus.setBackgroundResource(R.drawable.bg_status_normal);
            tvStatus.setTextColor(Color.BLACK);
            tvStatus.setText("Belum dibayar");
        }

        tvTenor.setText(String.format("%s", param.getPeriod() < 10 ? "0"+param.getPeriod() : param.getPeriod()));

        tvDatetime.setText(CommonUtils.formatDateBirth(param.getDueDate()));

        itemView.setOnClickListener(v -> listener.onClickDetailAngsuran(param));

    }
}
