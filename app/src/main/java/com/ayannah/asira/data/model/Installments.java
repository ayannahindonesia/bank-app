package com.ayannah.asira.data.model;

public class Installments {
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
}
