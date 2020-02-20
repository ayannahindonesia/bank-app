package com.ayannah.asira.adapter.viewHolder_agent;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentsClientVH extends RecyclerView.ViewHolder {

    @BindView(R.id.profilePict) ImageView ivProfilePict;
    @BindView(R.id.name) TextView tvName;
    @BindView(R.id.status) TextView tvStatus;
    @BindView(R.id.ajukan) LinearLayout ajukan;
    @BindView(R.id.id) TextView tvId;

    private CommonListListener.AgentsClientListener listener;

    public AgentsClientVH(View itemView, CommonListListener.AgentsClientListener listener){
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    public void bind(UserBorrower user){

        ImageUtils.displayImageFromUrlWithErrorDrawable(itemView.getContext(), ivProfilePict, user.getImage(), null);

        tvName.setText(user.getFullname());
        tvStatus.setText(user.getLoanStatus());
        tvId.setText(String.format("%s - %s", user.getBankName(), user.getBankAccountnumber()));

        itemView.setOnClickListener(v -> listener.onClickClient(user));
        ajukan.setOnClickListener(v -> listener.onClickAjukan(user));

    }
}
