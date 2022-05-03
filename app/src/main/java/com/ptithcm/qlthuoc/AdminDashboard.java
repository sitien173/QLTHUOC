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

import com.ptithcm.qlthuoc.Entity.AppUser;

public class AdminDashboard extends AppCompatActivity {
    ImageView orders, users, medicine;
    TextView adminInfo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static AppUser appUser;
    DbContext dbContext;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        setControl();
        setEvent();
        setConfig();
        initData();
    }
    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);

    }
    private void initData() {
        AppUser appUser = getPrincipal();
        adminInfo.setText(appUser.getHoten());
    }


    private void setControl(){
        orders = findViewById(R.id.ordes);
        users = findViewById(R.id.users);
        medicine = findViewById(R.id.medicine);
        adminInfo = findViewById(R.id.adminInfo);
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
                AppUser appUser = this.getPrincipal();
                Intent intent = new Intent(this, ProfileUser.class);

                intent.putExtra("edtUserName", appUser.getUsername());
                intent.putExtra("edtHoTen", appUser.getHoten());
                intent.putExtra("edtPassword", appUser.getPassword());
                intent.putExtra("role", appUser.getRole());
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

    @SuppressLint("Range")
    private AppUser getPrincipal(){
        if(appUser == null) {
            appUser = new AppUser();
            String username = sharedPreferences.getString("username", "");
            try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
                String query = "select * from AppUser where username = ?";
                Cursor cursor = db.rawQuery(query, new String[]{username});
                if (cursor != null) {
                    cursor.moveToFirst();
                    appUser.setHoten(cursor.getString(cursor.getColumnIndex("hoten")));
                    appUser.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    appUser.setRole(cursor.getString(cursor.getColumnIndex("role")));
                    appUser.setUsername(username);
                    appUser.setAvatar(cursor.getBlob(cursor.getColumnIndex("avatar")));
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appUser;
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
