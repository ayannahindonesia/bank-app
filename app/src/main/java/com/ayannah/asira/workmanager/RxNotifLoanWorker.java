package com.ayannah.asira.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.util.NotificationHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;

public class RxNotifLoanWorker extends RxWorker {

    private final String TAG = RxNotifLoanWorker.class.getSimpleName();

//    private SimpleDateFormat sdf_from_backend = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault());
    private SimpleDateFormat sdf_from_backend = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());
    private SimpleDateFormat sdf_base = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat view_sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    public RxNotifLoanWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {

        String tokenUser = getInputData().getString("userToken");
        String name = getInputData().getString("name");

        AndroidNetworking.get(BuildConfig.API_URL+"borrower/loan")
                .addHeaders("Authorization", tokenUser)
                .addQueryParameter("sort", "desc")
                .addQueryParameter("orderby", "id")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i=0; i< data.length(); i++){

                                String status = data.getJSONObject(i).getString("status");

                                //get tenor
                                int tenor = data.getJSONObject(i).getInt("installment");

                                //get distburse date untuk notif tiap bulan
                                String getDisburseDate = data.getJSONObject(i).getString("disburse_date");
                                Log.e(TAG, "getDisburseDate: "+getDisburseDate);

                                /*
                                Check tiap bulan (tergantung tenor), setiap 3 hari sebelum disburse date,
                                maka akan tampilkan notif
                                 */
                                Calendar curCalendar = Calendar.getInstance();
                                Calendar disburseCalendar = Calendar.getInstance();

                                Date disDate = sdf_from_backend.parse(getDisburseDate);
                                disburseCalendar.set(disDate.getYear(), disDate.getMonth(), disDate.getDay());
                                disburseCalendar.setTime(disDate);

                                for(int j = tenor; j > 0; j--){

                                    //add a month of disburse date
                                    disburseCalendar.add(Calendar.MONTH, 1);

                                    //set to String to get date format yyyy-MM-dd'T'hh:mm:ss'Z'
                                    String sCurrentDate = sdf_from_backend.format(curCalendar.getTime());
                                    String sDisburseDate = sdf_from_backend.format(disburseCalendar.getTime());

                                    //convert to date again
                                    Date curDate = sdf_from_backend.parse(sCurrentDate);
                                    Date disburseDate = sdf_from_backend.parse(sDisburseDate);

                                    //check perbedaan waktu jika kurang dari 3 hari
                                    long diff = disburseDate.getTime() - curDate.getTime();
                                    long days = diff / (24 * 60 * 60 *1000);
                                    Log.e(TAG, String.format("disdate: %s, days: %s", sDisburseDate, days));

                                    if((days == 3 || days == 2) && status.equals("approved")){

                                        callNotification(name, view_sdf.format(disburseDate));
                                        break;

                                    }

                                }
                            }

                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("ErrorWorkRx", anError.getErrorBody());

                    }
                });

        return Single.just(Result.success());
    }

    private void callNotification(String name, String dueDate){

        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.createNotification("Asira", String.format("Hi %s, tanggal %s menjadi tanggal untuk mencicil bayar pinjamanmu", name, dueDate));

    }
}
