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
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.util.NotificationHelper;
import com.google.gson.JsonArray;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class RxNotifLoanWorker extends RxWorker {

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

                                //count due date


                                Calendar calendar = Calendar.getInstance();
                                String curDate = sdf_from_backend.format(calendar.getTime());
                                String dueDate = data.getJSONObject(i).getString("due_date");

                                Date _dueDate= sdf_from_backend.parse(dueDate);
                                Date _curDate = sdf_from_backend.parse(curDate);

                                long diff = _dueDate.getTime() - _curDate.getTime();
                                long days = diff / (24 * 60 * 60 * 1000);

                                if(status.equals("approved") && days == 3){

                                    callNotification(name, view_sdf.format(_dueDate));

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

        return Single.just(Result.retry());
    }

    private void callNotification(String name, String dueDate){

        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.createNotification("Asira", String.format("Hi %s, %s jangan lupa bayar tagihan yah sayangku", name, dueDate));

    }
}
