package com.hack.server.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.hack.opensdk.R;

public class ForgroundService extends Service {

    private static final String FORGROUND_CHANNEL_ID = "hack_forground_id";
    private static final String FORGROUND_CHANNEL_NAME = "hack_forground_channel";
    private static final int FORGROUND_NOTIFICATION_ID = 1000000;

    @Override
    public void onCreate() {
        super.onCreate();
        startForgroundNotification(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForgroundNotification(Context context) {
        //1.create channel if needed
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            final NotificationChannel forgroundChannel = new NotificationChannel(FORGROUND_CHANNEL_ID,
                    FORGROUND_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(forgroundChannel);
        }

        //2.create notification
        final Notification notification;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            notification = new Notification();
        } else {

            Intent notificationIntent = new Intent();
            notificationIntent.setPackage(getPackageName());
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

            String name = String.valueOf(getApplicationInfo().loadLabel(getPackageManager()));
            notification = new Notification.Builder(this, FORGROUND_CHANNEL_ID)
                    .setContentTitle(getString(R.string.notification_running_title, name))
                    .setContentText(getString(R.string.notification_running_warn, name))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)
                    .build();
        }

        //3.start
        startForeground(FORGROUND_NOTIFICATION_ID, notification);
    }
}
