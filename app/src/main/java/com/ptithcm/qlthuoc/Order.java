package com.ptithcm.qlthuoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Order extends AppCompatActivity {
    EditText txtUsername, txtPhone, txtAddress;
    Button btnLogin, btnBack;
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order);
        setConfig();
        setControl();
        setEvent();
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    private void setEvent() {
        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnLogin);
    }
}