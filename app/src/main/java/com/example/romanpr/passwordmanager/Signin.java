package com.example.romanpr.passwordmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        /*
        Notifies the app whenever the user signs in or signs out.
         */
        signOut();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    DataMaster.userDb = new Database(user.getUid());
                    //DataMaster.userDb.getAccountList();
                    changeActivity(user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //createMasterAccount("roman.priscepov@hotmail.com", "123456789");
        //signOut();
        //signIn("romanpr@example.com", "1234567891011");
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
    Attempts to log in the user with their account's email and password passed as the arguments.
     */
    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete: " + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signIn:failed", task.getException());
                            Toast.makeText(Signin.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*
    Logs out the currently logged in user.
     */
    public void signOut() {

        // FirebaseAuth.getInstance().signOut();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mAuth.signOut();
            Log.d(TAG, "Signed out: " + currentUser.getUid());
        } else {
            Log.d(TAG, "No one to sign out");
        }
    }

    public void changeActivity(String uid) {
        Intent myIntent = new Intent(this, ShowServices.class);
        myIntent.putExtra("USER_ID", uid);
        startActivity(myIntent);
    }

    public void buttonClickLogin(View view) {
        EditText etUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText etPassword = (EditText) findViewById(R.id.editTextPassword);
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        DataMaster.masterHash = PMCrypto.whirlpoolDigest(password.getBytes());
        signIn("romanpr@example.com", "1234567891011");
        /*
        if(etUsername.getText().toString().length() < 0 || etPassword.getText().toString().length() < 5){
            Toast.makeText(this, "Username or Password is too short!", Toast.LENGTH_SHORT).show();
        }else{
            signIn(username, password);
        }*/
    }

    public void bRegister(View view) {
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }
}
