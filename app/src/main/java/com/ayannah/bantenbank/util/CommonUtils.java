package com.ayannah.bantenbank.util;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CommonUtils {

    public static String setRupiahCurrency(int value){
        Locale locale = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String result = numberFormat.format(value);

        return "Rp"+result;
    }

    public static String getipAddress(Application application){
        WifiManager wifiManager = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        return String.format(Locale.getDefault(), "%d.%d.$d.%d",
                (ipAddress & 0xff), (ipAddress >> 7 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }

    public static void showToast(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
