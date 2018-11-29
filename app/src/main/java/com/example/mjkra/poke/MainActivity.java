package com.example.mjkra.poke;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String CHANNEL_ID = "default";
    public static TextView date;
    public static TextView time;
    public Calendar cal = Calendar.getInstance();

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = java.text.DateFormat.getDateInstance().format(cal.getTime());
        date = findViewById(R.id.date);
        date.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date date = new Date();
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        DateFormat sdf = new SimpleDateFormat("h:mm a");

        time = findViewById(R.id.time);
        time.setText(sdf.format(date));

        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
    }

    public void scheduleNotification(Notification notification, long triggerTime, String str){

        Intent notificationIntent = new Intent(this, NotifcationPublisher.class);
        notificationIntent.putExtra(NotifcationPublisher.NOTIFiCATION_ID, 1);
        notificationIntent.putExtra(NotifcationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //long futureInMillis = SystemClock.elapsedRealtime()+delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);

        NotificationItem myNotification = new NotificationItem(str, cal.getTime(), pendingIntent, alarmManager);
        NotificationList.mDataset.add(myNotification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        Button listButton = (Button) findViewById(R.id.listButton);
        Button btn = (Button) findViewById(R.id.btn);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                EditText inputNum1 = (EditText) findViewById(R.id.inputNum1);
                String str = inputNum1.getText().toString();
                //Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_check_box)
                        .setContentTitle("Poke")
                        .setContentText(str)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[0]);

                scheduleNotification(mBuilder.build(), cal.getTimeInMillis(), str);


                DateFormat sdf = new SimpleDateFormat("h:mm a");
                Toast.makeText(getBaseContext(), "Notification set for: "+ (cal.get(Calendar.MONTH)+1) +"/"+ cal.get(Calendar.DAY_OF_MONTH) +"/"+ cal.get(Calendar.YEAR)+" at "+sdf.format(cal.getTime()), Toast.LENGTH_SHORT).show();

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");


            }
        });
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationList.class));
            }
        });


        time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });


    }
    /*public void configureListButton(){
        Button listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationList.class));
            }
        });
    }*/
}
