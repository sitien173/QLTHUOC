package com.ptithcm.qlthuoc.Order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.Adapter.CartAdapter;
import com.ptithcm.qlthuoc.Adapter.ListOrderAdapter;
import com.ptithcm.qlthuoc.AdminDashboard;
import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.HoaDon;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.R;

import java.util.ArrayList;


public class AdminListOrder extends AppCompatActivity {
    Button btnNewOrder, btnBack;
    DbContext dbContext;
    AppUser customer;
    ListView listView;
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    ListOrderAdapter listOrderAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order);
        setConfig();
        setControl();
        setEvent();

        listHoaDon = getListHoaDon();
        listView = (ListView)findViewById(R.id.listViewOrder);
        listOrderAdapter = new ListOrderAdapter(this, R.layout.item_order, listHoaDon, dbContext);
        listView.setAdapter(listOrderAdapter);
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

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, AdminDashboard.class));
        });
    }

    @SuppressLint("Range")
    public ArrayList<HoaDon> getListHoaDon() {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<HoaDon> listHoaDon = new ArrayList<>();
            String query = "SELECT * FROM HoaDon";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                int id_customer = cursor.getInt(cursor.getColumnIndex("id_customer"));

                // query get info customer
                String queryCustomer = "SELECT * FROM AppUser WHERE id = ?";
                Cursor cursorCustomer = db.rawQuery(queryCustomer, new String[]{ String.valueOf(id_customer) });
                cursorCustomer.moveToFirst();

                AppUser customer = new AppUser(cursorCustomer.getInt(0), cursorCustomer.getString(1), cursorCustomer.getString(2), cursorCustomer.getString(3), cursorCustomer.getBlob(4), cursorCustomer.getString(5), cursorCustomer.getString(6), cursorCustomer.getString(7));

                HoaDon hoaDon = new HoaDon(null, customer, cursor.getString(3), cursor.getFloat(4));
                hoaDon.setId(cursor.getInt(0));

                listHoaDon.add(hoaDon);
                cursor.moveToNext();
            }
            return listHoaDon;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }


    private void setControl() {
        btnNewOrder = findViewById(R.id.btnNewOrder);
        btnBack = findViewById(R.id.btnBack);
    }
}