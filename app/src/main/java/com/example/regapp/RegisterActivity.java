package com.example.regapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView big_header;
    private AppCompatButton user_regBtn;
    private AppCompatEditText input_name, input_age, reg_email, reg_pass;
    private ProgressBar indeterminateBar2;

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

        input_name = (AppCompatEditText) findViewById(R.id.input_name);
        input_age = (AppCompatEditText) findViewById(R.id.input_age);
        reg_email = (AppCompatEditText) findViewById(R.id.reg_email);
        reg_pass = (AppCompatEditText) findViewById(R.id.reg_pass);

        indeterminateBar2 = (ProgressBar) findViewById(R.id.indeterminateBar2);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.big_header:

            case R.id.user_regBtn:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }
}