package com.ayannah.asira.adapter.viewHolder_agent;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetilAngsuranVH extends RecyclerView.ViewHolder {

    @BindView(R.id.total)
    TextView tvTotal;

    @BindView(R.id.tenor)
    TextView tvTenor;

    public DetilAngsuranVH(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String s){

        tvTotal.setText(s);

    }
}
