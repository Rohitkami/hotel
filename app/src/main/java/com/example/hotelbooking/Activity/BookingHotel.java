package com.example.hotelbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.Model.BookingDone_model;
import com.example.hotelbooking.Model.RegisterUserDataModel;
import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookingHotel extends AppCompatActivity {

    TextView tv_minus_room_id,tv_room_id,tv_add_room_id,tv_roomtype_id,tv_hotelname_id;
    TextView tv_minus_guest_id,tv_guest_id,tv_add_guest_id,tv_datepicker_checkout_id,tv_datepicker_checkin_id;
    Button btn_submit_id;
    String guest_count,room_count,hotel_name,room_type,userid;
    DatePickerDialog StartTime;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;
    DatabaseReference mref;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    RegisterUserDataModel registerUserDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_hotel);
        hotel_name = getIntent().getStringExtra("hotel_name");
        room_type = getIntent().getStringExtra("room_type");
        room_count = getIntent().getStringExtra("room_count");
        guest_count = getIntent().getStringExtra("guest_count");
        userid = getIntent().getStringExtra("userid");
            /*                intent.putExtra("guest_count",hotelDetails_classList.get(position).getGuestcount());
                intent.putExtra("room_count",hotelDetails_classList.get(position).getRoomcount());
                intent.putExtra("hotel_name",hotelDetails_classList.get(position).getHotelname());
                intent.putExtra("room_type",hotelDetails_classList.get(position).getRoomtype());*/
        init();
        tv_hotelname_id.setText(hotel_name);
        tv_room_id.setText(room_count);
        tv_guest_id.setText(guest_count);
        tv_roomtype_id.setText(room_type);
        listener();
        call_visitordetails();
    }

    private void call_visitordetails() {
        currentUser = mAuth.getCurrentUser();
        progressDialog.show();
        mref.child("visitordetails").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if (dataSnapshot.exists()){
                    registerUserDataModel = dataSnapshot.getValue(RegisterUserDataModel.class);
                    Toast.makeText(BookingHotel.this, "username "+registerUserDataModel.getUsername(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BookingHotel.this, "Something went wrong try after sometime", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(BookingHotel.this, "Something went wrong try after sometime", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void listener() {
        tv_add_room_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0,r=0;
                try {
                    i = Integer.parseInt(tv_room_id.getText().toString());
                    r = Integer.parseInt(room_count);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (i<r){
                    i++;
                    tv_room_id.setText(i+"");
                }

            }
        });
        tv_add_guest_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0,g=0;
                try {
                    i = Integer.parseInt(tv_guest_id.getText().toString());
                    g = Integer.parseInt(guest_count);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (i<g){
                    i++;
                    tv_guest_id.setText(i+"");
                }

            }
        });
        tv_minus_room_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                try {
                    i = Integer.parseInt(tv_room_id.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (i>0){
                    i--;
                    tv_room_id.setText(i+"");
                }
            }
        });
        tv_minus_guest_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                try {
                    i = Integer.parseInt(tv_guest_id.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (i>0){
                    i--;
                    tv_guest_id.setText(i+"");
                }
            }
        });

        tv_datepicker_checkin_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkindate();
            }
        });
        tv_datepicker_checkout_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutdate();
            }
        });
        btn_submit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    save_booking();
                }
            }
        });
    }

    private void save_booking() {
        BookingDone_model bookingDone_model = new BookingDone_model();
        bookingDone_model.setGuest_count(tv_guest_id.getText().toString());
        bookingDone_model.setHotel_name(tv_hotelname_id.getText().toString());
        bookingDone_model.setRoom_count(tv_room_id.getText().toString());
        bookingDone_model.setRoom_type(tv_roomtype_id.getText().toString());
        bookingDone_model.setUserid(userid);
        bookingDone_model.setCheckin(tv_datepicker_checkin_id.getText().toString());
        bookingDone_model.setCheckout(tv_datepicker_checkout_id.getText().toString());
        bookingDone_model.setVisitoname(registerUserDataModel.getUsername().toString());
        bookingDone_model.setBooked_done("Done");

        currentUser = mAuth.getCurrentUser();
        progressDialog.show();
        mref.child("BookingDone").child(userid).child(currentUser.getUid())
                .setValue(bookingDone_model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             progressDialog.dismiss();
                Toast.makeText(BookingHotel.this, "Successfully booked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BookingHotel.this,HomeScreen.class);
                startActivity(intent);
                finish();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookingHotel.this, "unseccessfull booking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation() {

        if (tv_room_id.getText().toString().equalsIgnoreCase("0")){
            Toast.makeText(this, "Enter Valid room capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tv_guest_id.getText().toString().equalsIgnoreCase("0")){
            Toast.makeText(this, "Enter Valid guest capacity ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(tv_datepicker_checkin_id.getText().toString())){
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(tv_datepicker_checkout_id.getText().toString())){
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkoutdate() {
        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tv_datepicker_checkout_id.setText(year+"-"+dayOfMonth +"-"+monthOfYear);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    private void checkindate() {
        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tv_datepicker_checkin_id.setText(year+"-"+dayOfMonth +"-"+monthOfYear);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    private void init() {
        tv_hotelname_id = findViewById(R.id.tv_hotelname_id);
        tv_roomtype_id = findViewById(R.id.tv_roomtype_id);
        tv_minus_room_id = findViewById(R.id.tv_minus_room_id);
        tv_room_id = findViewById(R.id.tv_room_id);
        tv_add_room_id = findViewById(R.id.tv_add_room_id);

        tv_minus_guest_id = findViewById(R.id.tv_minus_guest_id);
        tv_guest_id = findViewById(R.id.tv_guest_id);
        tv_add_guest_id = findViewById(R.id.tv_add_guest_id);
        tv_datepicker_checkout_id = findViewById(R.id.tv_datepicker_checkout_id);
        tv_datepicker_checkin_id = findViewById(R.id.tv_datepicker_checkin_id);
        btn_submit_id = findViewById(R.id.btn_submit_id);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        registerUserDataModel = new RegisterUserDataModel();
    }
}
