package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

public class TmpImage {

    private int imgId;
    private String valBase64;

    public TmpImage() {
    }

    public TmpImage(int imgID) {
        this.imgId = imgID;
    }

    public int getImgId() {
        return imgId;
    }

    public String getValBase64() {
        return valBase64;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setValBase64(String valBase64) {
        this.valBase64 = valBase64;
    }
}
