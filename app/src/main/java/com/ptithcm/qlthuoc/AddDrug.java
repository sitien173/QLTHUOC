package com.ptithcm.qlthuoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddDrug extends AppCompatActivity {

    EditText txtTenThuoc, txtThanhPhan, txtDonViTinh, txtSoLuong, txtDonGia;
    Button btnAdd, uploadImg,btnBack;
    ImageView imgThuoc;
    byte[] file;
    private static final int REQUEST_UPLOAD_FILE = 102;
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
        uploadImg.setOnClickListener(view -> {
                    Intent intent = new Intent(this, UploadFile.class);
                    startActivityForResult(intent, REQUEST_UPLOAD_FILE);
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
        uploadImg = findViewById(R.id.btnSelectPhoto);
        imgThuoc = findViewById(R.id.imgThuoc);
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
            ct.put("hinhanh", file);
            db1.insert("Thuoc",null, ct);
            Toast.makeText(this, "Them thanh cong", Toast.LENGTH_LONG).show();
            // startActivity(new Intent(this, MainActivity.class));
        }catch (Exception e){
            Toast.makeText(this, "Them that bai "+e.getMessage()+"", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_UPLOAD_FILE)
            {
                file = data.getByteArrayExtra("img");
                Bitmap bitmap = BitmapFactory.decodeByteArray(file, 0, file.length);
                imgThuoc.setImageBitmap(bitmap);
                // avatar.setImageBitmap(bitmap);
            }
        }
    }
}
