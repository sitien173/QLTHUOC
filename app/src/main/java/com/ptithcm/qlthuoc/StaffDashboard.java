package com.ptithcm.qlthuoc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class StaffDashboard extends AppCompatActivity {
    ImageView imgMenu;
    LinearLayout layoutSale, layoutCrOrders;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff);
        setControl();
        setEvent();
    }

    private void setControl(){
        layoutSale = findViewById(R.id.layoutSale);
        layoutCrOrders = findViewById(R.id.layoutCrOrders);
        imgMenu = findViewById(R.id.iconMenu);
    }

    @SuppressLint("ResourceType")
    private void  setEvent(){
        /*imgMenu.setOnClickListener(view -> {
            setTitle(null);
            setContentView(R.menu.);
        });*/
    }
}
