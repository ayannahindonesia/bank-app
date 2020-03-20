package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Installments implements Parcelable {
    int index;
    String pokokPinjaman;
    String cicilanPokokPinjaman;
    String bunga;
    String angsuranPerBulan;
    String saldoPokokPinjaman;

    public Installments() {
    }

    public Installments(int index, String pokokPinjaman, String cicilanPokokPinjaman, String bunga, String angsuranPerBulan, String saldoPokokPinjaman) {
        this.index = index;
        this.pokokPinjaman = pokokPinjaman;
        this.cicilanPokokPinjaman = cicilanPokokPinjaman;
        this.bunga = bunga;
        this.angsuranPerBulan = angsuranPerBulan;
        this.saldoPokokPinjaman = saldoPokokPinjaman;
    }

    protected Installments(Parcel in) {
        index = in.readInt();
        pokokPinjaman = in.readString();
        cicilanPokokPinjaman = in.readString();
        bunga = in.readString();
        angsuranPerBulan = in.readString();
        saldoPokokPinjaman = in.readString();
    }

    public static final Creator<Installments> CREATOR = new Creator<Installments>() {
        @Override
        public Installments createFromParcel(Parcel in) {
            return new Installments(in);
        }

        @Override
        public Installments[] newArray(int size) {
            return new Installments[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPokokPinjaman() {
        return pokokPinjaman;
    }

    public void setPokokPinjaman(String pokokPinjaman) {
        this.pokokPinjaman = pokokPinjaman;
    }

    public String getCicilanPokokPinjaman() {
        return cicilanPokokPinjaman;
    }

    public void setCicilanPokokPinjaman(String cicilanPokokPinjaman) {
        this.cicilanPokokPinjaman = cicilanPokokPinjaman;
    }

    public String getBunga() {
        return bunga;
    }

    public void setBunga(String bunga) {
        this.bunga = bunga;
    }

    public String getAngsuranPerBulan() {
        return angsuranPerBulan;
    }

    public void setAngsuranPerBulan(String angsuranPerBulan) {
        this.angsuranPerBulan = angsuranPerBulan;
    }

    public String getSaldoPokokPinjaman() {
        return saldoPokokPinjaman;
    }

    public void setSaldoPokokPinjaman(String saldoPokokPinjaman) {
        this.saldoPokokPinjaman = saldoPokokPinjaman;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(pokokPinjaman);
        dest.writeString(cicilanPokokPinjaman);
        dest.writeString(bunga);
        dest.writeString(angsuranPerBulan);
        dest.writeString(saldoPokokPinjaman);
    }
}
