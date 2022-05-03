package com.ptithcm.qlthuoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ptithcm.qlthuoc.Entity.Thuoc;

import java.util.List;

public class EditDrug extends AppCompatActivity {
    Thuoc thuoc ;
    EditText txtTenThuoc, txtThanhPhan, txtDonViTinh, txtSoLuong, txtDonGia;
    Button btnEdit, btnSelectPhoto;
    ImageView imgThuoc;
    byte[] file;
    private static final int REQUEST_UPLOAD_FILE = 102;
    DbContext dbContext = new DbContext(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thuoc = (Thuoc) getIntent().getSerializableExtra("id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);


      Log.d("BBB",String.valueOf(thuoc.getSoluong()));
      Log.d("BBB",String.valueOf(thuoc.getDongia()));
      Log.d("BBB",String.valueOf(thuoc.getDonvitinh()));
        setControl();
        setEvent();
        txtTenThuoc.setText(thuoc.getTenthuoc());
        txtThanhPhan.setText(thuoc.getThanhphan());
        txtDonViTinh.setText(thuoc.getDonvitinh());
        txtSoLuong.setText(String.valueOf(thuoc.getSoluong()));
        txtDonGia.setText(String.valueOf(thuoc.getDongia()));

        Bitmap bitmap = BitmapFactory.decodeByteArray(thuoc.getHinhanh(), 0, thuoc.getHinhanh().length);
        imgThuoc.setImageBitmap(bitmap);
    }

    private void setEvent() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDrug(thuoc);
                startActivity(new Intent(EditDrug.this, QuanLiThuoc.class));
            }
        });

        btnSelectPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(this, UploadFile.class);
            startActivityForResult(intent, REQUEST_UPLOAD_FILE);
        });
    }
    private void setControl() {
        btnEdit = findViewById(R.id.btnEdit);
        txtTenThuoc = findViewById(R.id.editTextTextPersonName6);
        txtThanhPhan = findViewById(R.id.editTextTextPersonName7);
        txtDonViTinh = findViewById(R.id.editTextTextPersonName8);
        txtSoLuong = findViewById(R.id.editTextTextPersonName9);
        txtDonGia = findViewById(R.id.editTextTextPersonName10);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        imgThuoc = findViewById(R.id.imgThuoc);
    }

    public void updateDrug(Thuoc thuoc) {
        SQLiteDatabase db = dbContext.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tenthuoc", txtTenThuoc.getText().toString().trim());
        values.put("thanhphan", txtThanhPhan.getText().toString().trim());
        values.put("donvitinh", txtDonViTinh.getText().toString().trim());
        values.put("soluong", Integer.parseInt(txtSoLuong.getText().toString().trim()));
        values.put("dongia", Float.parseFloat(txtDonGia.getText().toString().trim()));
        values.put("hinhanh", file);
        db.update("Thuoc", values, "Id = ?", new String[] { String.valueOf(thuoc.getId()) });
        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_LONG).show();
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


//    public Thuoc getThuoc(int id) {
//        String query = "SELECT * FROM Thuoc where id = " + id ;
//        Thuoc thuoc = new Thuoc();
//        SQLiteDatabase db = dbContext.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//        Log.d("AAA",thuoc.getTenthuoc());
//
//
//        while(cursor.isAfterLast() == false) {
//           thuoc = new Thuoc(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4),cursor.getFloat(5) );
//            txtTenThuoc.setText(thuoc.getTenthuoc());
//            txtThanhPhan.setText(thuoc.getThanhphan());
//            txtDonViTinh.setText(thuoc.getDonvitinh());
//            txtSoLuong.setText(thuoc.getSoluong());
//            txtDonGia.setText(String.valueOf(thuoc.getDongia()));
//            cursor.moveToNext();}
//        return thuoc;
//
//        }


//    public Thuoc getThuoc(int id) {
//        // List<Thuoc>  listThuoc = new ArrayList<>();
//        String query = "SELECT * FROM Thuoc where id = " + id ;
//
//        SQLiteDatabase db = dbContext.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//
//        while(cursor.isAfterLast() == false) {
//            Thuoc thuoc = new Thuoc(cursor.getInt(0), cursor.getString(1));
//            listThuoc.add(thuoc);
//            cursor.moveToNext();
//        }
//        return listThuoc;
//    }
}