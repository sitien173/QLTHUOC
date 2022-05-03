package com.ptithcm.qlthuoc.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.qlthuoc.Adapter.OrderLineAdapter;
import com.ptithcm.qlthuoc.Adapter.drugAdapter;
import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.EditDrug;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.Order.AddInfoCustomer;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.QuanLiThuoc;
import com.ptithcm.qlthuoc.R;

import java.util.ArrayList;
import java.util.List;


public class ProductOrder extends AppCompatActivity {
    Button btnBack;
    //Button btnAddProduct;
    String username, phone, address;
    DbContext dbContext;
    private ListView listView;
    OrderLineAdapter orderLineAdapter;
    private ArrayList<Thuoc> arrListDrug = new ArrayList<>();
    private AppUser customer;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_line);
        setConfig();
        setControl();
        setEvent();
        fetchData();

        arrListDrug = getListDrug();
        listView = (ListView)findViewById(R.id.listViewOrderline);
        orderLineAdapter = new OrderLineAdapter(this, R.layout.item_orderline, arrListDrug, customer, dbContext);
        listView.setAdapter(orderLineAdapter);
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    private void setEvent() {
        //btnAddProduct.setOnClickListener(view -> {
            //startActivity(new Intent(this, Order.class));
        //});

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, AddInfoCustomer.class));
        });
    }

    private void fetchData() {
        customer = (AppUser) getIntent().getSerializableExtra("customer");
    }

    public ArrayList<Thuoc> getListDrug() {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<Thuoc> listDrug = new ArrayList<>();
            String query = "SELECT * FROM Thuoc";

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            while(cursor.isAfterLast() == false) {
                Thuoc thuoc = new Thuoc(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4),cursor.getFloat(6) );
                listDrug.add(thuoc);
                cursor.moveToNext();
            }
            return listDrug;
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnBack);
        //btnAddProduct = findViewById(R.id.btnAddProduct);
    }
}