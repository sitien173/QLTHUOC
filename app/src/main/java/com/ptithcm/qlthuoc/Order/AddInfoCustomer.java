package com.ptithcm.qlthuoc.Order;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.AdminDashboard;
import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.R;
import com.ptithcm.qlthuoc.StaffDashboard;

import java.sql.SQLOutput;


public class AddInfoCustomer extends AppCompatActivity {
    EditText txtUsername, txtPhone, txtAddress;
    Button btnAddInfoCustomer, btnBack;
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
            String CUSTOMER_ROLE = "CUSTOMER";
            try (SQLiteDatabase db = dbContext.getWritableDatabase())
            {
                Intent intent = new Intent(AddInfoCustomer.this, Order.class);
                String query = "SELECT * FROM AppUser WHERE username = ?";
                Cursor cursor = db.rawQuery(query, new String[]{ username });
                cursor.moveToFirst();
                AppUser customer;
                if(cursor.isAfterLast()) {
                    // add customer to DB
                    ContentValues ct = new ContentValues();
                    String DEFAULT_PASS = "1";

                    // làm cho username thành unique
                    //Getting the current date
                    Date date = new Date();
                    //This method returns the time in millis
                    long timeMilli = date.getTime();
                    String usernameLast = username.replaceAll(" ", "") + timeMilli;

                    ct.put("password", DEFAULT_PASS);
                    ct.put("hoten", username);
                    ct.put("username", usernameLast);
                    ct.put("phone", phone);
                    ct.put("address", address);
                    ct.put("avatar", avatarTemp);
                    ct.put("role", CUSTOMER_ROLE);
                    db.insert("AppUser",null, ct);

                    String queryInfoCustomerNew = "SELECT * FROM AppUser WHERE username = ? AND address = ? AND phone = ?";
                    Cursor cursorInfoCustomerNew = db.rawQuery(queryInfoCustomerNew, new String[]{usernameLast, address, phone});
                    cursorInfoCustomerNew.moveToFirst();

                    customer = new AppUser(cursorInfoCustomerNew.getInt(0), usernameLast, "1", username, avatarTemp, CUSTOMER_ROLE, address, phone);
                    intent.putExtra("customer", customer);
                } else {
                    customer = new AppUser(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                    intent.putExtra("customer", customer);
                }

                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage() + "", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        btnBack.setOnClickListener(view -> {
            String role = sharedPreferences.getString("role", "");
            switch (role)
            {
                case "ADMIN": {
                    startActivity(new Intent(this, AdminListOrder.class));
                    break;
                }
                case "USER": {
                    startActivity(new Intent(this, StaffDashboard.class));
                    break;
                }
                default: break;
            }
        });
    }

    private void setControl() {
        btnAddInfoCustomer = findViewById(R.id.btnAddInfoCustomer);
        btnBack = findViewById(R.id.btnBack);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
    }
}