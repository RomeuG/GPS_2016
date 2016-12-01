package com.example.romanpr.passwordmanager;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by romanpr on 11/25/2016.
 */

public class Database {

    private DatabaseReference database;
    private static final String TAG = "Database";

    public Database(String uid) {

        Log.d(TAG, "Received user ID: " + uid);
        database = FirebaseDatabase.getInstance().getReference().child(uid);
        Log.d(TAG, "Database constructor: Member variables instantiated");
    }

    private String sanitize(String str) {

        // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
        return str.replaceAll("[^a-zA-Z0-9]","");
    }

    private String getAccountId(String service, String username) {

        String accountId = sanitize(service) + '_' + sanitize(username);

        return accountId;
    }

    public void saveNewAccount(Account acc) {

        String accountId = getAccountId(acc.getService(), acc.getUsername());
        database.child(accountId).setValue(acc);
        Log.d(TAG, "New account added: " + accountId);
    }


    public void getAccount(String service, String username) {

        String accountId = getAccountId(service, username);
        ValueEventListener accountListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Account fetchedAccount = dataSnapshot.getValue(Account.class);
                Log.d(TAG, "Fetched account: " + fetchedAccount.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "getAccount:onCancelled", databaseError.toException());
            }
        };

        database.child(accountId).addListenerForSingleValueEvent(accountListener);
    }


    public void getAccountList() {

        ValueEventListener accountListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> accountList = dataSnapshot.getChildren();
                Log.d(TAG, "**************Account List**************");
                for (DataSnapshot account : accountList) {
                    Log.d(TAG, account.child("service").getValue().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "getAccountList:onCancelled", databaseError.toException());
            }
        };

        database.addListenerForSingleValueEvent(accountListener);
    }


    public void deleteAccount(String service, String username) {

        String accountId = getAccountId(service, username);
        database.child(accountId).removeValue();
        Log.d(TAG, "Account deleted: " + accountId);
    }


    public void updatePassword(String service, String username, String newPassword) {

        String accountId = getAccountId(service, username);
        database.child(accountId).child("password").setValue(newPassword);
        Log.d(TAG, "Password updated for: " + accountId);
    }

}
