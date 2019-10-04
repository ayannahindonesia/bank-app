package com.ayannah.asira.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.historyloan.HistoryLoanActivity;

public class NotificationHelper {

    private Context mContext;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder mBuilder;
    private static final String NOTIFICATION_CHANNEL_ID = "1001";

    public NotificationHelper(Context mContext){
        this.mContext = mContext;
    }

    public void createNotification(String title, String message){

        //create intent to pass it into pending intent, so if user click notification,
        //would be move to specific activity
        Intent resultIntent = new Intent(mContext, HistoryLoanActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //create pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //init Notification Compat
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.ic_monetization);
        mBuilder.setContentTitle(title);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        mBuilder.setAutoCancel(true);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setContentIntent(pendingIntent);

        //init notification manager
        notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        //check OS for notification purposes proses
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Loan Repayment", NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.CYAN);
            mChannel.enableVibration(true);

            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(mChannel);

        }

        notificationManager.notify(0, mBuilder.build());

    }

}
