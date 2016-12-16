package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class newService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service);
    }

    public void createAccount(View view) {

        String service = ((EditText) findViewById(R.id.etNServiceCS)).getText().toString();
        String username = ((EditText) findViewById(R.id.etUsernameCS)).getText().toString();
        String password = ((EditText) findViewById(R.id.etPasswordCS)).getText().toString();
        String passwordConfirm = ((EditText) findViewById(R.id.etConfirmPasswordCS)).getText().toString();

        // User input validation
        if (service.length() > 0 && username.length() > 0 && password.length() > 0 && password.equals(passwordConfirm)) {
            Account newAccount = new Account(service, username, password);
            Log.e("newService", newAccount.toString());
            DataMaster.userDb.saveNewAccount(newAccount);
            // Remind in 27 days
            long delay = 27 * 24 * 60 * 60 * 1000;
            MyNotificationPublisher.scheduleNotification(newService.this, 10000, 1234567);
            Toast.makeText(this, "New account successfully added", Toast.LENGTH_SHORT);
            Intent myIntent = new Intent(this, ShowServices.class);
            startActivity(myIntent);
        } else {
            Toast.makeText(this, "Some fields are invalid", Toast.LENGTH_SHORT);
        }
    }


}
