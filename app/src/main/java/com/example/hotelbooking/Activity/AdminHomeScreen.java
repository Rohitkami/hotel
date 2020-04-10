package com.example.hotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeScreen extends AppCompatActivity {

    TextView tv_adddetails_id,tv_viewbooking_id;
    ImageView img_logout_id;
    SharedPreferences pref;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
        init();
        listeners();
        Toast.makeText(this, "admin", Toast.LENGTH_SHORT).show();
    }

    private void listeners() {
        tv_viewbooking_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeScreen.this,AdminViewBooking.class);
                startActivity(intent);
            }
        });
        tv_adddetails_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeScreen.this,AdminAddDetaills.class);
                startActivity(intent);
            }
        });
        img_logout_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(AdminHomeScreen.this,LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        tv_viewbooking_id = findViewById(R.id.tv_viewbooking_id);
        tv_adddetails_id = findViewById(R.id.tv_adddetails_id);
        img_logout_id = findViewById(R.id.img_logout_id);
        pref = getApplicationContext().getSharedPreferences("Mypref",0);
        editor = pref.edit();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
        //super.onBackPressed();

    }
}
