package com.ayannah.asira.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.historyloan.HistoryLoanActivity;

public class CheckRepaymentLoanService extends JobService {


    NotificationManager notificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {

        String CHANNEL_ID = "repayment_loan";
        String CHANNEL_NAME = "channel_repayment";
        int notificationID = 222;

        Toast.makeText(this, "notif run", Toast.LENGTH_SHORT).show();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setLightColor(Color.CYAN);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(this, HistoryLoanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifCompat = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_asira_logo)
                .setContentTitle("Test Title")
                .setContentText("Test message")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(notificationID, notifCompat.build());


        jobFinished(params, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.e("CheckRepaymentLoan", "jobservice stop");

        return false;
    }
}


