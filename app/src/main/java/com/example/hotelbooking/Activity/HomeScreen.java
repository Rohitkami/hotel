package com.example.hotelbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.Adapter.RecyclerView_visitor;
import com.example.hotelbooking.Model.HotelDetails_class;
import com.example.hotelbooking.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomeScreen extends AppCompatActivity  {

    RecyclerView recyceler_id;
    LinearLayoutManager linearLayoutManager;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;
    DatabaseReference mref;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    ImageView img_logout_id;
    SharedPreferences pref;
    SharedPreferences.Editor editor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        init();
        call_summary();
        listener();

    }

    private void listener() {
        img_logout_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(HomeScreen.this,LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void call_summary() {
        progressDialog.show();;

        mref.child("Hoteldetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                List<HotelDetails_class> hotelDetails_classList = new ArrayList<>();
                while (dataSnapshotIterator.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshotIterator.next();
                    HotelDetails_class hotelDetails_class = dataSnapshotChild.getValue(HotelDetails_class.class);
                    hotelDetails_classList.add(hotelDetails_class);

                }
                recyceler_id.setAdapter(new RecyclerView_visitor( HomeScreen.this,hotelDetails_classList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });
    }


    private void init() {
        recyceler_id = findViewById(R.id.recyceler_id);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyceler_id.setLayoutManager(linearLayoutManager);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
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
      //  super.onBackPressed();

    }
}
