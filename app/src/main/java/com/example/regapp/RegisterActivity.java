package com.example.regapp;

import static androidx.core.util.PatternsCompat.EMAIL_ADDRESS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView big_header;
    private AppCompatButton user_regBtn;
    private AppCompatEditText editTextfullName, editAge, editEmail, editPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //implement firebaseObject
        mAuth = FirebaseAuth.getInstance();

        //implement user reg btn
        user_regBtn = (AppCompatButton) findViewById(R.id.user_regBtn);
        user_regBtn.setOnClickListener(this);

        big_header = (TextView) findViewById(R.id.big_header);
        big_header.setOnClickListener(this);

        editTextfullName = (AppCompatEditText) findViewById(R.id.input_name);
        editAge = (AppCompatEditText) findViewById(R.id.input_age);
        editEmail = (AppCompatEditText) findViewById(R.id.reg_email);
        editPassword = (AppCompatEditText) findViewById(R.id.reg_pass);

        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar2);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.big_header:
                //navigate to login page
                startActivity(new Intent(this, MainActivity.class));

            case R.id.user_regBtn:
                // get register activity
                registerUser();
                break;
        }

    }

    private void registerUser(){

        String input_name = Objects.requireNonNull(editTextfullName.getText()).toString().trim();
        String input_age = editAge.getText().toString().trim();
        String reg_pass = editPassword.getText().toString().trim();
        String reg_email = editEmail.getText().toString().trim();
//if empty name field
        if(input_name.isEmpty()) {
            editTextfullName.setError("full name is required! ");
            editTextfullName.requestFocus();

            return;
        }
//if empty age field
        if (input_age.isEmpty()){
            editAge.setError("input age");
            editAge.requestFocus();

            return;

        }
 //if empty password
        if(reg_pass.isEmpty()){
            editPassword.setError("password is required! ");
            editPassword.requestFocus();

            return;
        }

        //less than 6 digits password

        if (reg_pass.length() < 6){
            editPassword.setError("pls input not less than 6 digits! ");
            editPassword.requestFocus();

            return;

        }
//if email feild is empty
        if (reg_email.isEmpty()){
            editEmail.setError("email is required! ");
            editEmail.requestFocus();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            editEmail.setError("pls provide valid email");
            editEmail.requestFocus();
            return;
        }

    }
}