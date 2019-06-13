package com.ayannah.bantenbank.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.model.MenuProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuProductAdapter extends RecyclerView.Adapter<MenuProductAdapter.MenuProductVH> {

    private List<MenuProduct> menuProducts;
    private Application application;
    private MenuProductListener listener;

    public MenuProductAdapter(Application application){

        this.application = application;

        menuProducts = new ArrayList<>();
    }

    public void setOnClickListener(MenuProductListener listener){
        this.listener = listener;
    }

    public void setMenuProducts(List<MenuProduct> results){

        menuProducts.clear();

        menuProducts = results;

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MenuProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_product, parent, false);
        return new MenuProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuProductVH holder, int position) {

        holder.bind(menuProducts.get(position));

    }

    @Override
    public int getItemCount() {
        return menuProducts.size();
    }

    class MenuProductVH extends RecyclerView.ViewHolder{

        @BindView(R.id.iconProduct)
        ImageView ivIconProduct;

        @BindView(R.id.nameProduct)
        TextView tvNameProduct;

        MenuProductVH(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void bind(MenuProduct param){

            ivIconProduct.setImageResource(param.getLogoProduct());

            tvNameProduct.setText(param.getName());

            itemView.setOnClickListener(view -> listener.onClickMenu(param));

        }
    }

    public interface MenuProductListener{

        void onClickMenu(MenuProduct menuProduct);
    }
}
