package com.ptithcm.qlthuoc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {
    ImageView imgMenu;
    LinearLayout layoutSales, layoutOrders, layoutStaffs, layoutInvent, layoutCustoms;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        setControl();
        setEvent();
    }

    private void setControl(){
        layoutSales = findViewById(R.id.layoutSales);
        layoutOrders = findViewById(R.id.layoutOrders);
        layoutStaffs = findViewById(R.id.layoutStaffs);
        layoutInvent = findViewById(R.id.layoutInventory);
        layoutCustoms = findViewById(R.id.layoutCustomers);
        imgMenu = findViewById(R.id.iconMenu);
    }

    @SuppressLint("ResourceType")
    private void  setEvent(){
        imgMenu.setOnClickListener(view -> {
            setTitle(null);
            setContentView(R.menu.admin_navigative_menu);
        });
    }
}
