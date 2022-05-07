package com.ptithcm.qlthuoc.Order;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import com.ptithcm.qlthuoc.Adapter.OrderLineAdapter;
import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.HoaDon;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Order extends AppCompatActivity {
    String username, phone, address;
    int customerID;
    TextView txtUsername, txtPhone, txtAddress, txtTotalOrder;
    Button btnBack, btnExportOrder, btnAddOrderLine;
    AppUser customer;
    private int NOT_CREATED_ORDER = 2;
    CartAdapter cartAdapter;
    private ListView listView;
    DbContext dbContext;
    ArrayList<CT_BanLe> listCTBanLe = new ArrayList<>();
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
            txtUsername.setText("Khách hàng: " + customer.getHoten());
            txtPhone.setText("Số ĐT: " + customer.getPhone());
            txtAddress.setText("Địa chỉ: " + customer.getAddress());
            customerID = customer.getId();
        }

        listCTBanLe = getListBanLe();
        float totalOrder = getTotalOrder(listCTBanLe);
        txtTotalOrder.setText(String.valueOf(totalOrder));
        listView = (ListView)findViewById(R.id.listViewOrderline);
        cartAdapter = new CartAdapter(this, R.layout.item_detail_order, listCTBanLe, dbContext);
        listView.setAdapter(cartAdapter);
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    // kiểm tra các sản phẩm trong giỏ hàng có sản phẩm nào hết số lượng tồn hay không
    @SuppressLint("Range")
    private boolean haveProductNotEnoughQuantity(ArrayList<CT_BanLe> listCTBanLe) {
        for(CT_BanLe ctBanLe : listCTBanLe) {
            try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
                String query = "SELECT * FROM Thuoc WHERE id = ?";
                Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ctBanLe.getThuoc().getId())});
                cursor.moveToFirst();

                int quantityCTBanLe = ctBanLe.getSoluong();
                int quantityDrug = cursor.getInt(cursor.getColumnIndex(("soluong")));

                if(quantityCTBanLe > quantityDrug) {
                    return true;
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    // kiểm tra các sản phẩm trong giỏ hàng có sản phẩm nào hết số lượng tồn hay không
    @SuppressLint("Range")
    private void updateQuantityProduct(ArrayList<CT_BanLe> listCTBanLe) {
        for(CT_BanLe ctBanLe : listCTBanLe) {
            try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
                ContentValues values = new ContentValues();
                values.put("soluong", ctBanLe.getThuoc().getSoluong() - ctBanLe.getSoluong());
                db.update("Thuoc", values, "id = ?", new String[] { String.valueOf(ctBanLe.getThuoc().getId()) });
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(this, ProductOrder.class);
            i.putExtra("customer", customer);
            startActivity(i);
        });

        btnExportOrder.setOnClickListener(view -> {
            boolean isHaveProductSoldOut = haveProductNotEnoughQuantity(listCTBanLe);
            if(!isHaveProductSoldOut) {
                Intent i = new Intent(Order.this, OrderSuccess.class);
                long idHoaDonNew = 0;

                float totalOrder = getTotalOrder(listCTBanLe);

                // INSERT HOADON INTO DB
                try (SQLiteDatabase db = dbContext.getWritableDatabase()) {
                    ContentValues ct = new ContentValues();
                    ct.put("id_customer", customer.getId());
                    ct.put("total", totalOrder);
                    ct.put("ghichu", "note");
                    idHoaDonNew = db.insert("HoaDon",null, ct);
                } catch (Exception e) {
                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                i.putExtra("customer", customer);
                i.putExtra("id_hoadon", (int)idHoaDonNew);
                for(CT_BanLe ctBanLe : listCTBanLe) {
                    try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
                        ContentValues values = new ContentValues();
                        values.put("status", 3);
                        values.put("id_hoadon", (int)idHoaDonNew);
                        db.update("CT_BanLe", values, "status = ? AND id_customer = ? AND id_thuoc = ?", new String[] { String.valueOf(ctBanLe.getStatus()), String.valueOf(ctBanLe.getKhachhang().getId()), String.valueOf(ctBanLe.getThuoc().getId())});
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                updateQuantityProduct(listCTBanLe);

                startActivity(i);
            } else {
                Toast.makeText(this, "Xuất hóa đơn thất bại. Có sản phẩm trong giỏ hàng đã hết hàng", Toast.LENGTH_LONG).show();
                return;
            }
        });

        btnAddOrderLine.setOnClickListener(view -> {
            Intent i = new Intent(Order.this, ProductOrder.class);
            i.putExtra("customer", customer);
            startActivity(i);
        });
    }

    public float getTotalOrder(List<CT_BanLe> listCTBanLe) {
        float totalOrder = 0;
        for (CT_BanLe ctBanLe : listCTBanLe) {
            System.out.println("TOTAL: " + ctBanLe.getTotal());
            System.out.println("QUANTITY: " + ctBanLe.getSoluong());
            totalOrder += ctBanLe.getTotal();
        }
        return totalOrder;
    }

    @SuppressLint("Range")
    public ArrayList<CT_BanLe> getListBanLe() {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<CT_BanLe> listBanLe = new ArrayList<>();
            String query = "SELECT * FROM CT_BanLe WHERE status = ? AND id_customer = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(2), String.valueOf(customerID)});
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
        btnBack = findViewById(R.id.btnBack);
        btnExportOrder = findViewById(R.id.btnExportOrder);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);
        btnAddOrderLine = findViewById(R.id.btnAddOrderLine);
    }
}