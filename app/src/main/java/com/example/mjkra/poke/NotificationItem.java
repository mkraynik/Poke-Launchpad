package com.example.mjkra.poke;

import android.app.AlarmManager;
import android.app.PendingIntent;

import java.util.Calendar;
import java.util.Date;

public class NotificationItem {
    private String name;
    private Calendar cal;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    public NotificationItem(String str, Date d, PendingIntent pt, AlarmManager am){
        name = str;
        cal = Calendar.getInstance();
        cal.setTime(d);
        pendingIntent = pt;
        alarmManager = am;
    }

    public String getName(){
        return name;
    }

    public Calendar getCal(){
        return cal;
    }

    public PendingIntent getPt(){
        return pendingIntent;
    }

    public AlarmManager getAm(){
        return alarmManager;
    }
}
