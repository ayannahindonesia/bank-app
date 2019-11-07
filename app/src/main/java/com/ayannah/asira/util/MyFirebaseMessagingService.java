package com.ayannah.asira.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.historyloan.HistoryLoanActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());
    private SimpleDateFormat sdf_normal = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private NotificationManager notificationManager;
    private int NOTIF_LOAN = 1;
    private String CHANNEL_ID = "ASIRA_CHANNEL";
    private String CHANNEL_NAME = "ASIRA_LOAN";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.e(TAG, "onNewToken: "+token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, remoteMessage.getData().toString());

        String status = remoteMessage.getData().get("status");
        String id = remoteMessage.getData().get("id");
        String amount = remoteMessage.getData().get("loan");

        String message = String.format("Pinjaman kamu sebesar %s nomor %s telah %s", amount, id, status);
        sendNotification(message);

//        try {
//            JSONObject json = new JSONObject(remoteMessage.getNotification().getBody());
//            String updateTime = json.getString("updated_time");
//            String status = json.getString("status");
//            String id_loan = json.getString("id");
//
//            JSONObject objBorrower = json.getJSONObject("borrower_info");
//            String borrowerName = objBorrower.getString("fullname");
//
////            Date dUpdateTime = sdf.parse(updateTime);
//            String message = String.format("Hi %s, pinjaman kamu dengan nomor %s telah di %s pada tanggal %s. Mohon cek di ASIRA",
//                    borrowerName,
//                    id_loan,
//                    status,
//                    updateTime);
//
//            sendNotification(message);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }

        Intent intent = new Intent(this, HistoryLoanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_asira)
                .setContentTitle("ASIRA - AYANNAH")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        if(notificationManager != null){
            notificationManager.notify(NOTIF_LOAN, notificationBuilder.build());
        }
    }
}
