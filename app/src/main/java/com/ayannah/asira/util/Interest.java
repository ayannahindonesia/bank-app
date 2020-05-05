package com.ayannah.asira.util;

import com.ayannah.asira.data.model.Installments;

public class Interest {

    //PMT
    public static double PMT (double rate, double nper, double pv, double fv) {

        if (rate == 0) {
            return -(pv + fv) / nper;
        }

        double pvif = Math.pow(1+rate, nper);

        return Math.ceil(rate / (pvif - 1) * -(pv*pvif + fv));
    }

    //IPMT
    public static double IPMT(double pv, double pmt, double rate, double per) {

        double tmp = Math.pow(1+rate, per);
        return Math.ceil(0 - (pv*tmp*rate + pmt*(tmp-1)));

    }

    //PPMT
    public static double[] PPMT(double rate, double per, double nper, double pv, double fv) {

        double[] returnArray = new double[2];

        if (per < 1 || per >= nper+1) {
            returnArray[0] = 0;
            returnArray[1] = 0;
            return returnArray;
        }

        double pmt = PMT(rate, nper, pv, fv);
        double ipmt = IPMT(pv, pmt, rate, per-1);

        returnArray[0] = pmt - ipmt;
        returnArray[1] = ipmt;

        return returnArray;
    }

    //FLATANNUAL
    public static double[] FLATANNUAL(double rate, double v, double months) {

        double[] returnArray = new double[2];

        double monthlyPay = ((1 + rate) / months) * v;
        double totalPay = monthlyPay * months;

        returnArray[0] = monthlyPay;
        returnArray[1] = totalPay;

        return returnArray;
    }

    //ONTIMEPAY
    public static double[] ONTIMEPAY(double rate, double v, double months) {

        double[] returnArray = new double[2];

        double totalPay = v * (1 + rate);
        double monthlypay = totalPay / months;

        returnArray[0] = monthlypay;
        returnArray[1] = totalPay;

        return returnArray;

    }

}
