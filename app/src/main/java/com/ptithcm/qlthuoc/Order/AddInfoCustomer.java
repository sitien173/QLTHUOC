package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.R;


public class AddInfoCustomer extends AppCompatActivity {
    TextView txtUsername, txtPhone, txtAddress;
    Button btnAddInfoCustomer;
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_info_customer);
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
        btnAddInfoCustomer.setOnClickListener(view -> {
            startActivity(new Intent(this, ProductOrder.class));
        });
    }

    private void setControl() {
        btnAddInfoCustomer = findViewById(R.id.btnAddInfoCustomer);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
    }
}