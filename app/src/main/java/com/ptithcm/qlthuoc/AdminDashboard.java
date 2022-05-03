package com.ptithcm.qlthuoc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.Order.AddInfoCustomer;
import com.ptithcm.qlthuoc.Order.ProductOrder;

public class AdminDashboard extends AppCompatActivity {
    ImageView imgMenu;
    ImageView orders, users, medicine;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        setControl();
        setEvent();
    }

    private void setControl(){
        orders = findViewById(R.id.ordes);
        users = findViewById(R.id.users);
        medicine = findViewById(R.id.medicine);
        imgMenu = findViewById(R.id.iconMenu);
    }

    @SuppressLint("ResourceType")
    private void  setEvent(){
        imgMenu.setOnClickListener(view -> {
            setTitle(null);
            setContentView(R.menu.admin_navigative_menu);
        });

        users.setOnClickListener(view -> {
            startActivity(new Intent(this, ListUser.class));
        });

        orders.setOnClickListener(view -> {
            startActivity(new Intent(this, AddInfoCustomer.class));
        });

        medicine.setOnClickListener(view -> {
            startActivity(new Intent(this, QuanLiThuoc.class));
        });

    }
}
