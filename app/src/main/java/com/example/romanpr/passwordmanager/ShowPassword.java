package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

public class ShowPassword extends Activity {

    String[] accountInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);

        String selectedItem = getIntent().getStringExtra("selectedItem");
        Log.d("ShowPassword", selectedItem);
        accountInfo = selectedItem.split(" ");
        TextView tv = (TextView) findViewById(R.id.textViewServiceName);
        tv.setText(accountInfo[0]);
        DataMaster.userDb.getAccount(accountInfo[0], accountInfo[1]);

    }

    public void updateAcc(View view) {
        TextView username = (TextView) findViewById(R.id.textViewUserNameService);
        TextView password = (TextView) findViewById(R.id.textViewPasswordService);

        if(DataMaster.acc != null) {

                    username.setText(DataMaster.acc.getUsername());
                    password.setText(DataMaster.acc.getPassword());

        }
    }


    //This method generates a random password with or without special characters according to the variable sc
    public static String generateRandomPassword(int length, boolean sc) {

        Random random = new Random();
        char[] buf;
        char[] symbols;
        StringBuilder tmp = new StringBuilder();

        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        if (sc == true) {
            for (char ch = ' '; ch < '/'; ch++) {
                tmp.append(ch);
            }
        }
        symbols = tmp.toString().toCharArray();

        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        buf = new char[length];

        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Password Manager")
                .setContentText("Time to update your passwords.")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.key);

        Intent intent = new Intent(context, Signin.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        Log.d("Notif", "scheduleNotification finished");
    }

    public void updatePasswordService(View view) {
        String newPassword = generateRandomPassword(7, true);
        DataMaster.userDb.updatePassword(accountInfo[0], accountInfo[1], newPassword);
        scheduleNotification(ShowPassword.this, 10000, 1234567);
        TextView password = (TextView) findViewById(R.id.textViewPasswordService);
        password.setText(newPassword);
    }
}
