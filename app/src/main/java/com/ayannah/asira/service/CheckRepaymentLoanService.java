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
import com.ayannah.asira.util.NotificationHelper;

public class CheckRepaymentLoanService extends JobService {


    NotificationHelper notificationHelper;

    @Override
    public boolean onStartJob(JobParameters params) {

        notificationHelper = new NotificationHelper(this);
        notificationHelper.createNotification("Pelunasan pinjman", "Kurang 3 hari lagi, dibayar yak");


        jobFinished(params, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.e("CheckRepaymentLoan", "jobservice stop");

        return false;
    }
}


