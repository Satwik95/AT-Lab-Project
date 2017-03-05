package com.example.tstp.splashscreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            //already signed in
            Log.d("AUTH",auth.getCurrentUser().getEmail());
        } else {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setProviders(
                        AuthUI.FACEBOOK_PROVIDER,
                        AuthUI.EMAIL_PROVIDER,
                        AuthUI.GOOGLE_PROVIDER)
                .build(),RC_SIGN_IN);
    }
        findViewById(R.id.button).setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            if(resultCode == RESULT_OK)
            {

                Log.d("AUTH",auth.getCurrentUser().getEmail());
            }
            else
            {
                Log.d("AUTH","NOT AUTHENTICATED");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("AUTH","USER LOGGED OUT!");
                            finish();;
                        }
                    });
        }
    }
}
