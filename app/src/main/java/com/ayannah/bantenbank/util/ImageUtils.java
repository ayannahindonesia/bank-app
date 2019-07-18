package com.ayannah.bantenbank.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class ImageUtils {

    public static File compressImageFile(Context context, File imageFile){

        Compressor compressor = new Compressor(context)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath());

        try {
            return compressor.compressToFile(imageFile, String.format("IMAGE_%s.jpeg", System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;

    }
}
