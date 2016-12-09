package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class newService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service);
        //getActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public void createAccount(View view) {

        String service = ((EditText) findViewById(R.id.etNServiceCS)).getText().toString();
        String username = ((EditText) findViewById(R.id.etUsernameCS)).getText().toString();
        String password = ((EditText) findViewById(R.id.etPasswordCS)).getText().toString();
        String passwordConfirm = ((EditText) findViewById(R.id.etConfirmPasswordCS)).getText().toString();

        if (service != null && username != null && password != null && passwordConfirm != null &&
                service.length() > 0 && username.length() > 0 && password.length() > 0 && passwordConfirm.length() > 0 &&
                password.equals(passwordConfirm)) {

            Account newAccount = new Account(service, username, password);
            Log.e("newService", newAccount.toString());
            DataMaster.userDb.saveNewAccount(newAccount);

        }
    }


}
