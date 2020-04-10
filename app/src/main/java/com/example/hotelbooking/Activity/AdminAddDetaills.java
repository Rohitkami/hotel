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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.Model.HotelDetails_class;
import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AdminAddDetaills extends AppCompatActivity {

    EditText edt_hotelname_id;
    Spinner spinner_roomtype_id;
    TextView tv_minus_room_id,tv_room_id,tv_add_room_id;
    TextView tv_minus_guest_id,tv_guest_id,tv_add_guest_id,tv_datepicker_admin_id;
    Button btn_submit_id;
    DatePickerDialog StartTime;
    HotelDetails_class hotelDetails_class;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;
    DatabaseReference mref;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_detaills);
        init();
        listener();
        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tv_datepicker_admin_id.setText(year+"-"+dayOfMonth +"-"+monthOfYear);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void listener() {
        tv_add_room_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                try {
                     i = Integer.parseInt(tv_room_id.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (i<4){
                    i++;
                    tv_room_id.setText(i+"");
                }

            }
        });
        tv_add_guest_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                try {
                    i = Integer.parseInt(tv_guest_id.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (i<4){
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

        tv_datepicker_admin_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        StartTime .show();
            }
        });
        btn_submit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    save_hotel_details();
                }
            }
        });
    }

    private void save_hotel_details() {
        hotelDetails_class.setHotelname(edt_hotelname_id.getText().toString());
        hotelDetails_class.setRoomtype(spinner_roomtype_id.getSelectedItem().toString());
        hotelDetails_class.setDate_(tv_datepicker_admin_id.getText().toString());
        hotelDetails_class.setGuestcount(tv_guest_id.getText().toString());
        hotelDetails_class.setRoomcount(tv_room_id.getText().toString());
        String user = mAuth.getCurrentUser().getUid();
        hotelDetails_class.setUserid_admin(user);
        progressDialog.show();;
        mref.child("Hoteldetails").child(edt_hotelname_id.getText().toString()).setValue(hotelDetails_class)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(AdminAddDetaills.this,AdminHomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private boolean validation() {
        if (TextUtils.isEmpty(edt_hotelname_id.getText().toString())){
            Toast.makeText(this, "Enter hotel name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edt_hotelname_id.getText().toString().startsWith(" ")){
            Toast.makeText(this, "Enter valid hotel name", Toast.LENGTH_SHORT).show();
            return  false;
        }
        if (tv_room_id.getText().toString().equalsIgnoreCase("0")){
            Toast.makeText(this, "Enter Valid room capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tv_guest_id.getText().toString().equalsIgnoreCase("0")){
            Toast.makeText(this, "Enter Valid guest capacity ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(tv_datepicker_admin_id.getText().toString())){
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void init() {
        edt_hotelname_id = findViewById(R.id.edt_hotelname_id);
        spinner_roomtype_id = findViewById(R.id.spinner_roomtype_id);
        tv_minus_room_id = findViewById(R.id.tv_minus_room_id);
        tv_room_id = findViewById(R.id.tv_room_id);
        tv_add_room_id = findViewById(R.id.tv_add_room_id);

        tv_minus_guest_id = findViewById(R.id.tv_minus_guest_id);
        tv_guest_id = findViewById(R.id.tv_guest_id);
        tv_add_guest_id = findViewById(R.id.tv_add_guest_id);
        tv_datepicker_admin_id = findViewById(R.id.tv_datepicker_admin_id);
        btn_submit_id = findViewById(R.id.btn_submit_id);
        hotelDetails_class = new HotelDetails_class();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }
}
