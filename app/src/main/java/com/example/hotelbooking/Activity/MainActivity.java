package com.example.hotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.hotelbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        pref = getApplicationContext().getSharedPreferences("Mypref",0);
        editor = pref.edit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.getBoolean("login",false)){
                    if (pref.getString("logintype","").equalsIgnoreCase("Admin")){
                        Intent intent = new Intent(MainActivity.this, AdminHomeScreen.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }

                }else {
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                /*if (user!=null){
                    Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }*/

            }
    },4000);
}
}
