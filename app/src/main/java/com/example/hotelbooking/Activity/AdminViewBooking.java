package com.example.hotelbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hotelbooking.Adapter.AdminViewBooking_Adapter;
import com.example.hotelbooking.Model.BookingDone_model;
import com.example.hotelbooking.R;
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

public class AdminViewBooking extends AppCompatActivity {

    RecyclerView recyceler_id;
    LinearLayoutManager linearLayoutManager;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;
    DatabaseReference mref;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_booking);
        init();
        Call_viewBooking();
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
    }

    private void Call_viewBooking() {
        currentUser = mAuth.getCurrentUser();
        progressDialog.show();
        final List<BookingDone_model> bookingDone_models = new ArrayList<>();
        Query query = mref.child("BookingDone").child(currentUser.getUid());
//        Query query = mref.child("BookingDone").child(currentUser.getUid()).orderByChild("userid").equalTo(currentUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if(dataSnapshot.exists()){
                    progressDialog.dismiss();
                    Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                    List<BookingDone_model> bookingDone_models1 = new ArrayList<>();
                    while (dataSnapshotIterator.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshotIterator.next();
                        BookingDone_model bookingDone_model = dataSnapshotChild.getValue(BookingDone_model.class);
                        bookingDone_models1.add(bookingDone_model);
                        System.out.println("rohit11 "+dataSnapshot.getValue());
                        Toast.makeText(AdminViewBooking.this,"rohit12 "+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                    }
                    recyceler_id.setAdapter(new AdminViewBooking_Adapter( AdminViewBooking.this,bookingDone_models1));
                    /*for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        BookingDone_model bookingDone_model = dataSnapshot1.getValue(BookingDone_model.class);
                        bookingDone_models.add(bookingDone_model);
                        System.out.println("rohit "+dataSnapshot.getValue());
                        Toast.makeText(AdminViewBooking.this,"rohit "+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                    }

                    recyceler_id.setAdapter(new AdminViewBooking_Adapter( AdminViewBooking.this,bookingDone_models));*/
                }else {
                    Toast.makeText(AdminViewBooking.this, "Something went wrong. Kindly try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            /*{Taj hotel={nwTgzYdtiNUNSA41UpcTejmjIhb2={checkin=2020-9-3, room_count=1, guest_count=2, booked_done=Done, checkout=2020-25-3, userid=UtCuoGjSpWUIZaD17o01arlvhf82, visitoname=zen, hotel_name=Taj hotel, room_type=Deluxe}}, hotel mulund={847vpU6DOeXTwvjk8r2O1MloeNp1={checkin=2020-8-3, room_count=1, guest_count=1, booked_done=Done, checkout=2020-8-3, userid=UtCuoGjSpWUIZaD17o01arlvhf82, visitoname=rohit kami, hotel_name=hotel mulund, room_type=Standard}}}*/
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
/*        mref.child("BookingDone").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if(dataSnapshot.exists()){
                    //Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                    //List<BookingDone_model> bookingDone_models = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        BookingDone_model bookingDone_model = dataSnapshot1.getValue(BookingDone_model.class);
                        bookingDone_models.add(bookingDone_model);
                        Toast.makeText(AdminViewBooking.this,"rohit "+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                    }

                    recyceler_id.setAdapter(new AdminViewBooking_Adapter( AdminViewBooking.this,bookingDone_models));
                }else {
                    Toast.makeText(AdminViewBooking.this, "Something went wrong. Kindly try after some time", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });*/
    }
}
