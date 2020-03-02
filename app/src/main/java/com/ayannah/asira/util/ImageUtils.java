package com.ayannah.asira.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.ayannah.asira.R;
import com.ayannah.asira.util.glide.GlideApp;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

public class ImageUtils {

    private ImageUtils(){

    }

    public static void setImageBitmapWithEmptyImage(ImageView imageView, String imageBase64){

        if(imageBase64.length() > 15){

            byte[] decodeImageString = Base64.decode(imageBase64, Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImageString, 0, decodeImageString.length);

            if (bitmap!=null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_broken_image);
                imageView.setPadding(30,30,30,30);
            }

        }else{

            imageView.setImageResource(R.drawable.ic_broken_image);
            imageView.setPadding(30,30,30,30);
        }

    }

    public static void displayImageFromUrlWithErrorDrawable(Context context, ImageView imageView, String imageUrl, RequestListener<Drawable> listener) {

        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        GlideApp.with(context)
                .asBitmap()
                .apply(options)
                .load(imageUrl)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(20);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}
