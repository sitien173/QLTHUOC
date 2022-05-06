package com.ptithcm.qlthuoc.Order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.qlthuoc.Adapter.CartAdapter;
import com.ptithcm.qlthuoc.Adapter.OrderSuccessAdapter;
import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.R;

import java.util.ArrayList;
import java.util.List;


public class OrderSuccess extends AppCompatActivity {
    String username, phone, address;
    int customerID;
    TextView txtUsername, txtPhone, txtAddress, txtTotalOrder;
    Button btnNewOrder;
    ListView listView;
    AppUser customer;
    int id_hoadon;
    OrderSuccessAdapter orderSuccessAdapter;
    ArrayList<CT_BanLe> listCTBanLe = new ArrayList<>();
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success);
        setConfig();
        setControl();
        setEvent();

        // get and set value from screen add_info_customer to product_order
        customer = (AppUser) getIntent().getSerializableExtra("customer");
        id_hoadon = (Integer) getIntent().getSerializableExtra("id_hoadon");
        System.out.println("IDHOADON: " + id_hoadon);
        if(customer != null) {
            txtUsername.setText("Khách hàng: " + customer.getUsername());
            txtPhone.setText("Số ĐT: " + customer.getPhone());
            txtAddress.setText("Địa chỉ: " + customer.getAddress());
            customerID = customer.getId();
        }

        listCTBanLe = getListBanLe(id_hoadon);
        float totalOrder = getTotalOrder(listCTBanLe);
        txtTotalOrder.setText(String.valueOf(totalOrder));
        listView = (ListView)findViewById(R.id.listViewOrderline);
        orderSuccessAdapter = new OrderSuccessAdapter(this, R.layout.item_order_success, listCTBanLe, dbContext);
        listView.setAdapter(orderSuccessAdapter);
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
    }

    public float getTotalOrder(List<CT_BanLe> listCTBanLe) {
        float totalOrder = 0;
        for (CT_BanLe ctBanLe : listCTBanLe) {
            totalOrder += ctBanLe.getTotal();
        }
        return totalOrder;
    }

    @SuppressLint("Range")
    public ArrayList<CT_BanLe> getListBanLe(int id_hoadon) {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<CT_BanLe> listBanLe = new ArrayList<>();
            String query = "SELECT * FROM CT_BanLe WHERE status = ? AND id_customer = ? AND id_hoadon = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(3), String.valueOf(customerID), String.valueOf(id_hoadon)});
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                int COLUMN_INDEX_ID_THUOC = cursor.getColumnIndex("id_thuoc");

                // tạo thuốc
                String queryDrug = "SELECT * FROM Thuoc WHERE id = ?";
                Cursor cursorDrug = db.rawQuery(queryDrug, new String[]{String.valueOf(cursor.getString(COLUMN_INDEX_ID_THUOC))});
                cursorDrug.moveToFirst();
                byte[] imageDrug = cursorDrug.getBlob(5);
                Thuoc newDrug = new Thuoc(cursorDrug.getInt(0), cursorDrug.getString(1), cursorDrug.getString(2), cursorDrug.getString(3), cursorDrug.getInt(4), cursorDrug.getFloat(6));
                newDrug.setHinhanh(imageDrug);

                // tạo chi tiết đơn hàng
                CT_BanLe ctBanLe = new CT_BanLe(newDrug, null, cursor.getInt(cursor.getColumnIndex("soluong")), cursor.getFloat(cursor.getColumnIndex("total")), cursor.getInt(cursor.getColumnIndex("status")), customer);
                listBanLe.add(ctBanLe);

                cursor.moveToNext();
            }
            return listBanLe;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void setControl() {
        btnNewOrder = findViewById(R.id.btnNewOrder);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);
    }
}