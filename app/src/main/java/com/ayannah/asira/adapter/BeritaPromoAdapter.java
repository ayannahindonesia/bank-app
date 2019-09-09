package com.ayannah.asira.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.BeritaPromo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeritaPromoAdapter extends RecyclerView.Adapter<BeritaPromoAdapter.BeritaPromoVH> {

    private Context mContext;
    private List<BeritaPromo> lists;

    public BeritaPromoAdapter(Context mContext){
        this.mContext = mContext;

        lists = new ArrayList<>();
    }

    public void setBeritaNews(List<BeritaPromo> datas){
        lists.clear();
        lists = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeritaPromoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_berita_news, parent, false);
        return new BeritaPromoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaPromoVH holder, int position) {

        holder.bind(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class BeritaPromoVH extends RecyclerView.ViewHolder{

        @BindView(R.id.titleItem)
        TextView title;

        @BindView(R.id.descItem)
        TextView desc;

        @BindView(R.id.seeMore)
        TextView seeMore;

        @BindView(R.id.imgBanner)
        ImageView imgBanner;


        BeritaPromoVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BeritaPromo param){

            imgBanner.setImageResource(param.getImg());

            title.setText(param.getTitle());

            desc.setText(param.getDescription());

            seeMore.setOnClickListener(view -> Toast.makeText(mContext, "See More...", Toast.LENGTH_SHORT).show());
        }

    }
}
