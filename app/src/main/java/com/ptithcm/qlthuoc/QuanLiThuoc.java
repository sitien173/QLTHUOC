package com.ptithcm.qlthuoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ptithcm.qlthuoc.Adapter.drugAdapter;
import com.ptithcm.qlthuoc.Entity.NhaThuoc;
import com.ptithcm.qlthuoc.Entity.Thuoc;

import java.util.ArrayList;
import java.util.List;


public class QuanLiThuoc extends AppCompatActivity implements RecyclerListener {
    private ArrayList<Thuoc> listData = new ArrayList<>(); ;
    RecyclerView recyclerView ;
    drugAdapter drugAdapter;
    Button btnThem;
    DbContext dbContext  = new DbContext(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_thuoc);
        setControl();
        setEvent();

        getAllThuoc(listData);
        drugAdapter = new drugAdapter(this,this::onItemClick);
        drugAdapter.setData(listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(drugAdapter);

    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(QuanLiThuoc.this, AddDrug.class));
            }
        });
//        btnThem.setOnClickListener(view -> {
//            startActivity(new Intent(this, AddDrug.class));
//        });
    }

    private void setControl() {
        recyclerView = findViewById(R.id.recycler);
        btnThem = findViewById(R.id.btnThem);
    }
//    public void addDrug() {
//
//        try (SQLiteDatabase db1 = dbContext.getWritableDatabase())
//        {
//            ContentValues ct = new ContentValues();
//            ct.put("tenthuoc", "parracetadsa1");
//           // ct.put("donvitinh", "vien");
//
//            db1.insert("Thuoc",null, ct);
//            Toast.makeText(this, "Them thanh cong", Toast.LENGTH_LONG).show();
//           // startActivity(new Intent(this, MainActivity.class));
//        }catch (Exception e){
//            Toast.makeText(this, "Them that bai "+e.getMessage()+"", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }

    public List<Thuoc> getAllThuoc(List<Thuoc> listThuoc) {
       // List<Thuoc>  listThuoc = new ArrayList<>();
        String query = "SELECT * FROM Thuoc" ;

        SQLiteDatabase db = dbContext.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Thuoc thuoc = new Thuoc(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4),cursor.getFloat(6) );
            listThuoc.add(thuoc);
            cursor.moveToNext();
        }
        return listThuoc;
    }
//      " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
//              " \"tenthuoc\" TEXT NOT NULL, " +
//              " \"thanhphan\" TEXT DEFAULT NULL, " +
//              " \"donvitinh\" TEXT DEFAULT NULL, " +
//              " \"soluong\" INTEGER DEFAULT NULL, " +
//              " \"hinhanh\" BLOB, " +
//              " \"dongia\" FLOAT DEFAULT NULL );";


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(QuanLiThuoc.this,EditDrug.class);
        intent.putExtra("id", listData.get(position));
        startActivity(intent);
    }
}

