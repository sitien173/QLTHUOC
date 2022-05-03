package com.ptithcm.qlthuoc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {
    ImageView orders, users, medicine;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DbContext dbContext;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        setControl();
        setEvent();
        setConfig();
    }
    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);

    }

    private void setControl(){
        orders = findViewById(R.id.ordes);
        users = findViewById(R.id.users);
        medicine = findViewById(R.id.medicine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("Range")
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.account:
                String username = sharedPreferences.getString("username", "");
                String hoten = "";
                String pass = "";
                String role = "";
                try ( SQLiteDatabase db = dbContext.getReadableDatabase() ) {
                    String query = "select * from AppUser where username = ?";
                    Cursor cursor = db.rawQuery(query, new String[]{username});
                    if(cursor != null){
                        cursor.moveToFirst();
                        hoten = cursor.getString(cursor.getColumnIndex("hoten"));
                        pass = cursor.getString(cursor.getColumnIndex("password"));
                        role = cursor.getString(cursor.getColumnIndex("role"));
                        cursor.close();
                    }
                }catch (Exception e) { return false; }
                Intent intent = new Intent(this, ProfileUser.class);

                intent.putExtra("edtUserName", username);
                intent.putExtra("edtHoTen", hoten);
                intent.putExtra("edtPassword", pass);
                intent.putExtra("role", role);
                startActivity(intent);
                return true;
            case R.id.logout:
                editor.remove("is_login");
                editor.remove("username");
                editor.remove("role");
                editor.commit();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void  setEvent(){
        users.setOnClickListener(view -> {
            startActivity(new Intent(this, ListUser.class));
        });

        medicine.setOnClickListener(view -> {
            startActivity(new Intent(this, QuanLiThuoc.class));
        });

    }
}
