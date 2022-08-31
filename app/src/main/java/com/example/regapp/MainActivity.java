package com.example.regapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set register on click
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //remember it is a better practice to use if statement
        switch (v.getId()){
            case R.id.register:
                //onclick sent to RegisterActivicty
                startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}