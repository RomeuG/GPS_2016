package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import static com.google.android.gms.internal.zzs.TAG;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.uid;

/**
 * Created by romanpr on 11/25/2016.
 */

public class Database {


    private DatabaseReference database;
    private static final String TAG = "Database";

    /*
    Instantiates the database member variable with a DatabaseReference to the part of the database
    relevant to the currently logged in user.
     */
    public Database(String uid) {

        Log.d(TAG, "Received user ID: " + uid);
        database = FirebaseDatabase.getInstance().getReference().child(uid);
        Log.d(TAG, "Database constructor: Member variables instantiated");
    }

    /*
    Cleans the string of any characters forbidden to be part of the key in Firebase.
     */
    private String sanitize(String str) {

        // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
        return str.replaceAll("[^a-zA-Z0-9]","");
    }

    /*
    Generates account ID based on the service and username of the account.
     */
    private String getAccountId(String service, String username) {

        String accountId = sanitize(service) + '_' + sanitize(username);

        return accountId;
    }

    /*
    Commits the new account object to the database.
     */
    public void saveNewAccount(Account acc) {

        String accountId = getAccountId(acc.getService(), acc.getUsername());
        database.child(accountId).setValue(acc);
        Log.d(TAG, "New account added: " + accountId);
    }

    /*
    Fetched an account from the database based on the service and username associated with it.
     */
    public void getAccount(String service, String username) {

        String accountId = getAccountId(service, username);
        ValueEventListener accountListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Account fetchedAccount = dataSnapshot.getValue(Account.class);
                Log.d(TAG, "Fetched account: " + fetchedAccount.toString());
                DataMaster.acc = fetchedAccount;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "getAccount:onCancelled", databaseError.toException());
            }
        };

        database.child(accountId).addListenerForSingleValueEvent(accountListener);
    }

    /*
    Retrieves the names of the services the user has passwords stored too.
     */
    public void getAccountList() {

        ValueEventListener accountListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> accountList = dataSnapshot.getChildren();
                Log.d(TAG, "**************Account List**************");
                //DataMaster.services = new ArrayList<>();
                for (DataSnapshot account : accountList) {
                    Log.d(TAG, account.child("service").getValue().toString());
                    DataMaster.services.add(account.child("service").getValue().toString() + " "
                    + account.child("username").getValue().toString());
                }
                Log.e(TAG, DataMaster.services.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "getAccountList:onCancelled", databaseError.toException());
            }
        };

        database.addListenerForSingleValueEvent(accountListener);
    }

    /*
    Deletes the account associated with the passed service and username.
     */
    public void deleteAccount(String service, String username) {

        String accountId = getAccountId(service, username);
        database.child(accountId).removeValue();
        Log.d(TAG, "Account deleted: " + accountId);
    }

    /*
    Changes the password.
     */
    public void updatePassword(String service, String username, String newPassword) {

        String accountId = getAccountId(service, username);

        SecretKey key = PMCrypto.AESDeriveKey(DataMaster.masterHash, DataMaster.acc.salt.getBytes());
        String newPasswordEncrypted = PMCrypto.AESEncryptPBKDF2(newPassword, key, DataMaster.acc.iv);
        database.child(accountId).child("password").setValue(newPasswordEncrypted);
        Log.d(TAG, "Password updated for: " + accountId);
    }

}
