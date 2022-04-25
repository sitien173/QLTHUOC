package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.R;


public class OrderSuccess extends AppCompatActivity {
    TextView txtUsername, txtPhone, txtAddress, txtTotalOrder;
    Button btnNewOrder;
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success);
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
        btnNewOrder.setOnClickListener(view -> {
            startActivity(new Intent(this, Order.class));
        });
    }

    private void setControl() {
        btnNewOrder = findViewById(R.id.btnNewOrder);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);
    }
}