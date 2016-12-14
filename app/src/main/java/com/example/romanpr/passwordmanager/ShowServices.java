package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowServices extends Activity {

    ListView listView;

    private static final String TAG = "ShowServices";
    //private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uid = getIntent().getStringExtra("USER_ID");
        Log.d(TAG, "Passed user ID: " + uid);
        //database = FirebaseDatabase.getInstance().getReference().child(uid);
       if (DataMaster.services == null) {
           DataMaster.services = new ArrayList<>();
       }

        setContentView(R.layout.activity_show_services);
       /* try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                DataMaster.services);*/
        listView = (ListView) findViewById(R.id.list_viewShowServices);


        /*listView.setAdapter(adapter);
        DataMaster.userDb.getAccountList();
        adapter.notifyDataSetChanged();*/
        //listView.invalidate();
        getAccountList();
        //new Thr().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = String.valueOf(adapterView.getItemAtPosition(i));
                Intent it = new Intent(ShowServices.this, ShowPassword.class);
                it.putExtra("selectedItem", selectedItem);
                startActivity(it);
            }
        });
    }



    public void addAccount(View view) {

        Intent it = new Intent(this, newService.class);
        startActivity(it);
    }

   /* public void updateList(View view) {
        adapter.notifyDataSetChanged();
    }
*/




    /*
   Retrieves the names of the services the user has passwords stored too.
    */

    public void getAccountList() {

        ValueEventListener accountListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> accountList = dataSnapshot.getChildren();
                Log.d(TAG, "**************Account List**************");
                DataMaster.services = new ArrayList<>();
                for (DataSnapshot account : accountList) {
                    Log.d(TAG, account.child("service").getValue().toString());
                    DataMaster.services.add(account.child("service").getValue().toString()
                        + " " + account.child("username").getValue().toString());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ShowServices.this,
                        android.R.layout.simple_list_item_1, DataMaster.services);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "getAccountList:onCancelled", databaseError.toException());
            }
        };

        DataMaster.userDb.getDatabase().addListenerForSingleValueEvent(accountListener);
    }

   /* class Thr extends AsyncTask<Object, String, Void> {

        ArrayAdapter<String> thisAdapter;

        @Override
        protected Void doInBackground(Object... voids) {

            while(true) {
                DataMaster.userDb.getAccountList();
                if(!DataMaster.services.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Information received", Toast.LENGTH_LONG);
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            thisAdapter = adapter;
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            thisAdapter.notifyDataSetChanged();
            Log.e(TAG, "onPostExecute: ok cheguei aqui");
        }
    }*/
}
