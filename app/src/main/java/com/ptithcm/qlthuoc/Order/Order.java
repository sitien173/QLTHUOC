package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.R;

import java.io.Serializable;
import java.util.ArrayList;


public class Order extends AppCompatActivity {
    String username, phone, address;
    TextView txtUsername, txtPhone, txtAddress, txtTotalOrder;
    Button btnBack, btnExportOrder, btnAddOrderLine;
    AppUser customer;
    private int NOT_CREATED_ORDER = 2;
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

        customer = (AppUser) getIntent().getSerializableExtra("customer");
        if(customer != null) {
            txtUsername.setText("Khách hàng: " + customer.getUsername());
            txtPhone.setText("Số ĐT: " + customer.getPhone());
            txtAddress.setText("Địa chỉ: " + customer.getAddress());
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
            Intent i = new Intent(Order.this, ProductOrder.class);
            i.putExtra("customer", customer);
            startActivity(i);
        });
    }

    private void getTotalOrder() {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<CT_BanLe> listCTBanLe = new ArrayList<>();

            String query = "SELECT * FROM CT_BanLe WHERE status = 2";

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            while(cursor.isAfterLast() == false) {
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
        }
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