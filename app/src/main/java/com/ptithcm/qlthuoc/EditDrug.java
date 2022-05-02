package com.ptithcm.qlthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ptithcm.qlthuoc.Entity.Thuoc;

import java.util.List;

public class EditDrug extends AppCompatActivity {

    EditText txtTenThuoc, txtThanhPhan, txtDonViTinh, txtSoLuong, txtDonGia;
    Button btnEdit;
    DbContext dbContext = new DbContext(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);
      Thuoc thuoc = (Thuoc) getIntent().getSerializableExtra("id");

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
    }

    private void setEvent() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void setControl() {
        btnEdit = findViewById(R.id.btnEdit);
        txtTenThuoc = findViewById(R.id.editTextTextPersonName6);
        txtThanhPhan = findViewById(R.id.editTextTextPersonName7);
        txtDonViTinh = findViewById(R.id.editTextTextPersonName8);
        txtSoLuong = findViewById(R.id.editTextTextPersonName9);
        txtDonGia = findViewById(R.id.editTextTextPersonName10);
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