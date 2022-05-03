package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.R;


public class AddInfoCustomer extends AppCompatActivity {
    EditText txtUsername, txtPhone, txtAddress;
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
            String username = txtUsername.getText().toString().trim();
            String phone = txtPhone.getText().toString().trim();
            String address = txtAddress.getText().toString().trim();

            if(username.equals("") || phone.equals("") || address.equals("")) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                return;
            }

            byte[] avatarTemp = null;
            AppUser customer = new AppUser(username, "1", username, avatarTemp, "CUSTOMER", address, phone);
            Intent i = new Intent(AddInfoCustomer.this, ProductOrder.class);
            i.putExtra("customer", customer);
            startActivity(i);
        });
    }

    private void setControl() {
        btnAddInfoCustomer = findViewById(R.id.btnAddInfoCustomer);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
    }
}