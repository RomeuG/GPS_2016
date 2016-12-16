package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ShowPassword extends Activity {

    String[] accountInfo;
    private static final String TAG = "ShowPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);

        String selectedItem = getIntent().getStringExtra("selectedItem");
        Log.d("ShowPassword", selectedItem);
        accountInfo = selectedItem.split(" ");
        TextView tv = (TextView) findViewById(R.id.textViewServiceName);
        tv.setText(accountInfo[0]);
        getAccount(accountInfo[0], accountInfo[1]);

        ImageButton btUsernameCopy = (ImageButton) findViewById(R.id.ibUsernameCopy);
        ImageButton btPasswordCopy = (ImageButton) findViewById(R.id.ibPasswordCopy);

        btUsernameCopy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView username = (TextView) findViewById(R.id.textViewUserNameService);
                copyToClipboard(username.getText().toString());
            }
        });

        btPasswordCopy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView password = (TextView) findViewById(R.id.textViewPasswordService);
                copyToClipboard(password.getText().toString());
            }
        });
    }

    public void copyToClipboard(String text) {
        ClipData data = ClipData.newPlainText("", text);
        Context context = getApplicationContext();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(data);
    }

    public boolean tooLate(String updateDateString) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date updateDate;
        boolean late = false;
        try {
            updateDate = df.parse(updateDateString);
            //30*24*60*60*1000
            long delay = 18*1000;
            Date now = new Date();
            Log.d("ShowPassword", "Now: " + now.getTime());
            Log.d("ShowPassword", "Expiration: " + (updateDate.getTime() + delay));

            if (now.getTime() > (updateDate.getTime() + delay)) {
                late = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return late;
    }

    // Fetches an account from the database based on the service and username associated with it
    public void getAccount(String service, String username) {

        String accountId = Database.getAccountId(service, username);
        ValueEventListener accountListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Account fetchedAccount = dataSnapshot.getValue(Account.class);
                Log.d(TAG, "Fetched account: " + fetchedAccount.toString());
                DataMaster.acc = fetchedAccount;

                TextView username = (TextView) findViewById(R.id.textViewUserNameService);
                TextView password = (TextView) findViewById(R.id.textViewPasswordService);

                // Set the fields' values to the account's credentials
                if(fetchedAccount != null) {
                    username.setText(fetchedAccount.getUsername());
                    if (!tooLate(fetchedAccount.getLastUpdated())) {
                        password.setText(fetchedAccount.getDecryptedPassword());
                    } else {
                        password.setText("Password expired");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getAccount:onCancelled", databaseError.toException());
            }
        };

        DataMaster.userDb.getDatabase().child(accountId).addListenerForSingleValueEvent(accountListener);
    }

    // Generates a random password with or without special characters according to the argument sc
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

    public void updatePasswordService(View view) {
        String newPassword = generateRandomPassword(7, true);
        DataMaster.userDb.updatePassword(accountInfo[0], accountInfo[1], newPassword);
        // Remind in 27 days
        long delay = 27 * 24 * 60 * 60 * 1000;
        MyNotificationPublisher.scheduleNotification(ShowPassword.this, 10000, 1234567);
        TextView password = (TextView) findViewById(R.id.textViewPasswordService);
        password.setText(newPassword);
    }

    public void deleteUserAccount(View view) {

        if (DataMaster.acc != null) {
            DataMaster.userDb.deleteAccount(DataMaster.acc.getService(), DataMaster.acc.getUsername());
        }
        Intent myIntent = new Intent(this, ShowServices.class);
        startActivity(myIntent);
    }
}
