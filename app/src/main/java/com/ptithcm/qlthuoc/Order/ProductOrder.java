package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Order.AddInfoCustomer;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.R;


public class ProductOrder extends AppCompatActivity {
    String username, phone, address;
    Button btnAddProduct, btnBack;
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

        // get and set value from screen add_info_customer to product_order
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            phone = extras.getString("phone");
            address = extras.getString("address");
        }
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    private void setEvent() {
        btnAddProduct.setOnClickListener(view -> {
            Intent i = new Intent(ProductOrder.this, Order.class);
            i.putExtra("username", username);
            i.putExtra("phone", phone);
            i.putExtra("address", address);
            startActivity(i);
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, AddInfoCustomer.class));
        });
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnBack);
        btnAddProduct = findViewById(R.id.btnAddProduct);
    }
}