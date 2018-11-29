package com.example.mjkra.poke;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class NotifcationPublisher extends BroadcastReceiver {
    //Variables
        public static String NOTIFiCATION_ID = "notification-id";
        public static String NOTIFICATION = "notification";


    @Override
    public void onReceive(Context context, Intent intent) {
        
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra((NOTIFICATION));
        //int id = intent.getIntExtra(NOTIFiCATION_ID, 0);
        int id = (int)((new Date().getTime()/1000L)%Integer.MAX_VALUE);
        notificationManager.notify(id, notification);
    }
}
