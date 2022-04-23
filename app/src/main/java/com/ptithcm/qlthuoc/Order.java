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
    TextView txtUsername, txtPhone, txtAddress, txtTotalOrder;
    Button btnBack, btnExportOrder;
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
        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnExportOrder.setOnClickListener(view -> {
            startActivity(new Intent(this, OrderSuccess.class));
        });
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnBack);
        btnExportOrder = findViewById(R.id.btnExportOrder);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);
    }
}