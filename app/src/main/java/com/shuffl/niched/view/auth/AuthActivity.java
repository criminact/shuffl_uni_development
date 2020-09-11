package com.shuffl.niched.view.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.shuffl.niched.R;
import com.shuffl.niched.utils.firebase.FirebaseObject;
import com.shuffl.niched.view.home.MainActivity;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseObject.getFirebaseUser() != null){
            //startActivity(new Intent(this, MainActivity.class));
        }
    }
}