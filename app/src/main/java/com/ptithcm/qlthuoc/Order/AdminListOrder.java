package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.R;


public class AdminListOrder extends AppCompatActivity {
    Button btnNewOrder;
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_line);
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
            startActivity(new Intent(this, AddInfoCustomer.class));
        });
    }

    private void setControl() {
        btnNewOrder = findViewById(R.id.btnNewOrder);
    }
}