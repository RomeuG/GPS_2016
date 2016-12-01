package com.example.romanpr.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String uid = getIntent().getStringExtra("USER_ID");
        Log.d(TAG, "Passed user ID: " + uid);


        Database db = new Database(uid);
        Account acc = new Account("facebook", "roman.priscepov@hotmail.com", "pssvrev4885ghe8(*&&#@");
        Account acc2 = new Account("google", "roman.priscepov@goole.com", "pss8934438hfhueri*(*!*(@#JJN");
        db.saveNewAccount(acc);
        db.saveNewAccount(acc2);

        db.getAccount("facebook", "roman.priscepov@hotmail.com");
        db.updatePassword("facebook", "roman.priscepov@hotmail.com", "newPassword*(&*^(&&(*&&^^*");
        db.getAccount("facebook", "roman.priscepov@hotmail.com");
        db.getAccountList();
        db.deleteAccount("facebook", "roman.priscepov@hotmail.com");

    }

    public void signOut() {

        FirebaseAuth mAuth =  FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mAuth.signOut();
            Log.d(TAG, "Signed out: " + currentUser.getUid());
        } else {
            Log.d(TAG, "No one to sign out");
        }
    }
}
