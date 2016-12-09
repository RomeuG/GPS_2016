package com.example.romanpr.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        /*
        Notifies the app whenever the user signs in or signs out.
         */
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    changeActivity(user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Log.d(TAG, "onStart:addAuthStateListener");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            Log.d(TAG, "onStop:removeAuthStateListener");
        }
    }

    /*
    If the new account was created, the user is also signed in,
    and the AuthStateListener runs the onAuthStateChanged callback.
    In the callback, you can use the getCurrentUser method
    to get the user's account data.
     */
    private void createMasterAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "createMasterAccount:onComplete: " + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "createMasterAccount:failed", task.getException());
                            Toast.makeText(RegisterActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void changeActivity(String uid) {
        Intent myIntent = new Intent(this, ShowServices.class);
        myIntent.putExtra("USER_ID", uid);
        startActivity(myIntent);
    }

    public void buttonClickRegister(View view) {

        EditText etUsername = (EditText) findViewById(R.id.editTextRegisterUsername);
        EditText etPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        EditText etConfirmPassword = (EditText) findViewById(R.id.editTextConfimePassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        DataMaster.masterHash = PMCrypto.whirlpoolDigest(password.getBytes());

        if(username.length() < 1 || password.length() < 1 || confirmPassword.length() < 1)
            Toast.makeText(this, "Username or password is too short", Toast.LENGTH_SHORT);
        else{
            if(!(password.equals(confirmPassword)))
            {
                Toast.makeText(this, "Passwords don't match!!", Toast.LENGTH_SHORT);
            }
            else{
                createMasterAccount(username, password);
            }
        }

    }
}
