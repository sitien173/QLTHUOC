package com.ptithcm.qlthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDrug extends AppCompatActivity {

    EditText txtTenThuoc, txtThanhPhan, txtDonViTinh, txtSoLuong, txtDonGia;
    Button btnAdd,btnBack;
    DbContext dbContext = new DbContext(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddrug);
        setControl();
        setEvent();
    }

    private void setEvent() {
       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addDrug();
               startActivity(new Intent(AddDrug.this, QuanLiThuoc.class));
           }
       });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDrug.this, QuanLiThuoc.class));
            }
        });
    }

    private void setControl() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.Back);
        txtTenThuoc = findViewById(R.id.editTextTextPersonName);
        txtThanhPhan = findViewById(R.id.editTextTextPersonName2);
        txtDonViTinh = findViewById(R.id.editTextTextPersonName3);
        txtSoLuong = findViewById(R.id.editTextTextPersonName4);
        txtDonGia = findViewById(R.id.editTextTextPersonName5);
    }

    public void addDrug() {

        try (SQLiteDatabase db1 = dbContext.getWritableDatabase())
        {
            ContentValues ct = new ContentValues();
            ct.put("tenthuoc", txtTenThuoc.getText().toString().trim());
            ct.put("thanhphan", txtThanhPhan.getText().toString().trim());
            ct.put("donvitinh", txtDonViTinh.getText().toString().trim());
            ct.put("soluong", Integer.parseInt(txtSoLuong.getText().toString().trim()));
            ct.put("dongia", Float.parseFloat(txtDonGia.getText().toString().trim()));

            db1.insert("Thuoc",null, ct);
            Toast.makeText(this, "Them thanh cong", Toast.LENGTH_LONG).show();
            // startActivity(new Intent(this, MainActivity.class));
        }catch (Exception e){
            Toast.makeText(this, "Them that bai "+e.getMessage()+"", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
