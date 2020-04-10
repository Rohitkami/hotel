package com.example.hotelbooking.Activity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.Model.RegisterUserDataModel;
import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    TextInputEditText edt_username_id, edt_password_id;
    Button btn_submit_id;
    TextView tv_register_id;
    private FirebaseAuth mAuth;
    private String TAG="log";
    SharedPreferences pref;
    SharedPreferences.Editor editor ;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        init();
        listener();

    }

    private void listener() {
        tv_register_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_submit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    progressDialog.setMessage("Processing request...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(edt_username_id.getText().toString(),edt_password_id.getText().toString()).
                            addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
                                        Toast.makeText(LoginScreen.this, "Successfull", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "signInWithEmail:success");
                                        editor.putString("email_id",edt_username_id.getText().toString());
                                        editor.putBoolean("login",true);
                                        editor.commit();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user,progressDialog);
                                    }
                                    else {
                                        Toast.makeText(LoginScreen.this, "UnSuccessfull", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void updateUI(FirebaseUser user, final ProgressDialog progressDialog) {

        progressDialog.show();
        myRef.child("visitordetails").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if (dataSnapshot!=null&&dataSnapshot.exists()){

                    RegisterUserDataModel receivedData = dataSnapshot.getValue(RegisterUserDataModel.class);

                    if (receivedData.getLogin_type().toString().equalsIgnoreCase("Admin")){
                        editor.putString("logintype","Admin");
                        editor.commit();
                        Intent intent = new Intent(LoginScreen.this, AdminHomeScreen.class);
                        startActivity(intent);
                        finish();
                    }else {
                        editor.putString("logintype","visitor");
                        editor.commit();
                        Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                System.out.println(databaseError.toString());
            }
        });

    }

    private boolean validate() {
        if (TextUtils.isEmpty(edt_username_id.getText().toString())){
            edt_username_id.setError("Enter UserName");
            return  false;
        }
        if (edt_username_id.getText().toString().startsWith(" ")){
            edt_username_id.setError("Valid email");
            return false;
        }
        if (TextUtils.isEmpty(edt_password_id.getText().toString())){
            edt_password_id.setError("Enter Password");
            return  false;
        }
        return true;
    }

    private void init() {
        edt_password_id = findViewById(R.id.edt_password_id);
        edt_username_id = findViewById(R.id.edt_username_id);
        btn_submit_id = findViewById(R.id.btn_submit_id);
        tv_register_id = findViewById(R.id.tv_register_id);
        mAuth = FirebaseAuth.getInstance();
        pref = getApplicationContext().getSharedPreferences("Mypref",0);
        editor = pref.edit();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        progressDialog = new ProgressDialog(this);
    }
}
