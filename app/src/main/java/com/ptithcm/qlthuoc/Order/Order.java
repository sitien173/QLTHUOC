package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.R;


public class Order extends AppCompatActivity {
    String username, phone, address;
    TextView txtUsername, txtPhone, txtAddress, txtTotalOrder;
    Button btnBack, btnExportOrder, btnAddOrderLine;
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

        // get and set value from screen add_info_customer to product_order
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            phone = extras.getString("phone");
            address = extras.getString("address");

            txtUsername.setText("Khách hàng: " + username);
            txtPhone.setText("Số ĐT: " + phone);
            txtAddress.setText("Địa chỉ: " + address);
        }
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, ProductOrder.class));
        });

        btnExportOrder.setOnClickListener(view -> {
            Intent i = new Intent(Order.this, OrderSuccess.class);
            i.putExtra("username", username);
            i.putExtra("phone", phone);
            i.putExtra("address", address);
            startActivity(i);
        });

        btnAddOrderLine.setOnClickListener(view -> {
            startActivity(new Intent(this, ProductOrder.class));
        });
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnBack);
        btnExportOrder = findViewById(R.id.btnExportOrder);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);
        btnAddOrderLine = findViewById(R.id.btnAddOrderLine);
    }
}