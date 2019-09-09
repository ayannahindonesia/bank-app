package com.ayannah.asira.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.MenuProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuProductAdapter extends RecyclerView.Adapter<MenuProductAdapter.MenuProductVH> {

    private List<MenuProduct> menuProducts;
    private List<BankService.Data> menus;

    private Application application;
    private MenuProductListener listener;

    public MenuProductAdapter(Application application){

        this.application = application;

        menuProducts = new ArrayList<>();
        menus = new ArrayList<>();
    }

    public void setOnClickListener(MenuProductListener listener){
        this.listener = listener;
    }

    public void setMenuProducts(List<MenuProduct> results){

        menuProducts.clear();

        menuProducts = results;

        notifyDataSetChanged();

    }


    public void setMenuService(List<BankService.Data> data){

        menus.clear();

        menus = data;

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

        holder.bind(menus.get(position));

    }

    @Override
    public int getItemCount() {
        return menus.size();
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

        private void bind(BankService.Data param){

            /*
            <======== showing data static menu style ========>
             */

//            ivIconProduct.setImageResource(param.getLogoProduct());
//
//            tvNameProduct.setText(param.getName());
//
//            itemView.setOnClickListener(view -> listener.onClickMenu(param));

            /*
            <========= showing data static menu styl e=======>
             */



//            ivIconProduct.setImageResource(param.getLogoProduct());

            ivIconProduct.setImageResource(R.drawable.ic_menu_pns);

            tvNameProduct.setText(param.getName());

            itemView.setOnClickListener(view -> listener.onClickMenu(param));

        }
    }

    public interface MenuProductListener{

        void onClickMenu(BankService.Data menuProduct);
    }
}
