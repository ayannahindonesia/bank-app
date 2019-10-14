package com.ayannah.asira.adapter;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.R;
import com.ayannah.asira.data.local.PreferenceDataSource;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.MenuProduct;
import com.ayannah.asira.util.ImageUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuProductAdapter extends RecyclerView.Adapter<MenuProductAdapter.MenuProductVH> {

    private final String TAG = MenuProductAdapter.class.getSimpleName();

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

            PreferenceDataSource preferenceDataSource = new PreferenceDataSource(application);

            AndroidNetworking.get(BuildConfig.API_URL_LENDER + "admin/image/{file_id}")
                    .addHeaders("Authorization", preferenceDataSource.getAdminTokenLender())
                    .addPathParameter("file_id", String.valueOf(param.getImageId()))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String sImage = response.getString("image_string");
                                Log.e(TAG, sImage);

                                ImageUtils.setImageBitmapWithEmptyImage(ivIconProduct, sImage);

//                                byte[] decodedString = Base64.decode(sImage, Base64.DEFAULT);
//                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
//                                ivIconProduct.setImageBitmap(decodedByte);

                            } catch (Exception e) {
                                e.printStackTrace();
//                                Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                ivIconProduct.setImageResource(R.drawable.ic_broken_image);
//                                ivIconProduct.setPadding(30,30,30,30);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                            ANError anError1 = (ANError) anError;
                            if(anError1.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                                Toast.makeText(application, "Connection Error", Toast.LENGTH_SHORT).show();
                            } else {

                                if(anError1.getErrorBody() != null){

                                    try {
                                        JSONObject jsonObject = new JSONObject(anError1.getErrorBody());
                                        Toast.makeText(application, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            ivIconProduct.setImageResource(R.drawable.ic_broken_image);
                            ivIconProduct.setPadding(30,30,30,30);
                        }
                    });

            tvNameProduct.setText(param.getName());

            itemView.setOnClickListener(view -> listener.onClickMenu(param));

        }
    }

    public interface MenuProductListener{

        void onClickMenu(BankService.Data menuProduct);
    }
}
