package com.example.hotelbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hotelbooking.Model.RegisterUserDataModel;
import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edt_password_id, edt_mobileno_id, edt_email_id, edt_username_id;
    Button btn_submit_reg_id;
    private FirebaseAuth mAuth;
    RegisterUserDataModel registerUserDataModel;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;
    DatabaseReference mref;
    ProgressDialog progressDialog;
    Spinner spinner_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        setContentView(R.layout.activity_register);
        init();
        listener();

    }

    private void listener() {
        btn_submit_reg_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    create_user();
                }
            }
        });

        edt_username_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(" ")) {
                    edt_username_id.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void create_user() {
        progressDialog.setMessage("Processing request...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(edt_email_id.getText().toString(), edt_password_id.getText().toString()).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            save_userData(currentUser);
                            Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            /*Intent intent = new Intent(RegisterActivity.this, LoginScreen.class);
                            startActivity(intent);
                            finish();*/
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void save_userData(FirebaseUser currentUser) {
        registerUserDataModel.setEmailid(edt_email_id.getText().toString());
        registerUserDataModel.setUsername(edt_username_id.getText().toString());
        registerUserDataModel.setMobileNo(edt_mobileno_id.getText().toString());
        registerUserDataModel.setPasswrd(edt_password_id.getText().toString());
        registerUserDataModel.setLogin_type(spinner_id.getSelectedItem().toString());

        String userid = currentUser.getUid();

        //mref.child("visitordetails").child(userid);
        progressDialog.show();
        mref.child("visitordetails").child(userid).setValue(registerUserDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validation() {
        if (TextUtils.isEmpty(edt_username_id.getText().toString())) {
            edt_username_id.setError("Enter Name");
            return false;
        }
        if (edt_username_id.getText().toString().startsWith(" ")) {
            edt_username_id.setError("Enter Valid name");
            return false;
        }
        if (TextUtils.isEmpty(edt_email_id.getText().toString())) {
            edt_email_id.setError("Enter Email");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edt_email_id.getText().toString()).matches()) {
            edt_email_id.setError("Enter Valid Mail");
            return false;
        }

        if (TextUtils.isEmpty(edt_mobileno_id.getText().toString())) {
            edt_mobileno_id.setError("Enter Mobile No.");
            return false;
        }
        if (edt_mobileno_id.getText().toString().length() != 10) {
            edt_mobileno_id.setError("Enter valid Mobile No.");
            return false;
        }
        return true;
    }

    private void init() {
        edt_username_id = findViewById(R.id.edt_username_id);
        edt_email_id = findViewById(R.id.edt_email_id);
        edt_mobileno_id = findViewById(R.id.edt_mobileno_id);
        edt_password_id = findViewById(R.id.edt_password_id);
        btn_submit_reg_id = findViewById(R.id.btn_submit_reg_id);
        spinner_id = findViewById(R.id.spinner_id);
        registerUserDataModel = new RegisterUserDataModel();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }
}
