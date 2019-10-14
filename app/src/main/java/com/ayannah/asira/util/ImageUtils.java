package com.ayannah.asira.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.ayannah.asira.R;

public class ImageUtils {

    public static void setImageBitmapWithEmptyImage(ImageView imageView, String imageBase64){

        if(imageBase64.length() > 15){

            byte[] decodeImageString = Base64.decode(imageBase64, Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImageString, 0, decodeImageString.length);

            imageView.setImageBitmap(bitmap);

        }else{

            imageView.setImageResource(R.drawable.ic_broken_image);
        }

    }

}
