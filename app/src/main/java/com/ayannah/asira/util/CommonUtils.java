package com.ayannah.asira.util;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

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

    public  static String formatDateBirth(String dateBirth){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdfBirth = new SimpleDateFormat("dd MMM yyyy");

        Date sDate = new Date();
        try {
            sDate = sdf.parse(dateBirth);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdfBirth.format(sDate);
    }

    public  static String formatDateTimeForDB(String dateBirth){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat sdfBirth = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        Date sDate = new Date();
        try {
            sDate = sdfBirth.parse(dateBirth);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(sDate);
    }

    public static String commonErrorFormat(Throwable error){

        String result = null;

        ANError anError = (ANError) error ;

        if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
            result = "Tidak Ada Koneksi";
        } else if (anError.getErrorBody() != null) {

            try {

                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                result = jsonObject.optString("message");

            } catch (JSONException e) {

                e.printStackTrace();
            }

        } else {
            result = "Terjadi kesalahan";
        }

        return result;

    }

    public static String errorResponseWithStatusCode(Throwable error){

        String result = null;

        ANError anError = (ANError) error;

        if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){

            result = "Tidak ada koneksi internet";

        }else if(anError.getErrorCode() == HttpsURLConnection.HTTP_BAD_GATEWAY ||
                anError.getErrorCode() == HttpsURLConnection.HTTP_SERVER_ERROR){

            result = String.format("Code: %s\nSedang dalam perbaikan, silakan coba beberapa saat lagi.", anError.getErrorCode());


        }else {

            if(anError.getErrorBody() != null){

                try {

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    result = jsonObject.optString("message");

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        }

        return result;

    }

    public static String errorResponseGetCode(Throwable error) {

        String result;

        ANError anError = (ANError) error;

        if(anError.getErrorCode() != 0){

            result = String.valueOf(anError.getErrorCode());

        }else {

            result = "Internet terputus";

        }

        return result;

    }

}
