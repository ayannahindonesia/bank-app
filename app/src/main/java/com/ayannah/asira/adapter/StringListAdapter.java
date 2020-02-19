package com.ayannah.asira.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.StringListVH> {

    private List<String> menus;
    private StringListListener listener;

    public StringListAdapter(List<String> menus){
        this.menus = menus;
    }

    public void setOnClickStringListener(StringListListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public StringListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_string, parent, false);

        return new StringListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringListVH holder, int position) {

        holder.bind(menus.get(position));

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    class StringListVH extends RecyclerView.ViewHolder{

        @BindView(R.id.tvName)
        TextView name;

        StringListVH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(String x){

            name.setText(x);

            itemView.setOnClickListener(v -> listener.onClickItem(x));
        }
    }

    public interface StringListListener{

        void onClickItem(String item);

    }
}