package com.ptithcm.qlthuoc;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ptithcm.qlthuoc.Entity.AppUser;

import java.util.ArrayList;
import java.util.HashSet;
import com.ptithcm.qlthuoc.Entity.*;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername,txtPassword;
    TextView txtRegistration;
    Button btnLogin;
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static AppUser userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setConfig();
        setControl();
        setEvent();

        // redirect
        if(sharedPreferences.getBoolean("is_login", false)){
            Toast.makeText(this, "Bạn đã login", Toast.LENGTH_LONG).show();
        }
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    private void setEvent() {
        btnLogin.setOnClickListener(view -> {
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            userLogin = getUserLogin(username,password);
            Boolean islogin = isLogin(username,password);
            if(islogin)
            {
                // redirect to dashboard
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ListUser.class));
            }
            else
            {
                Toast.makeText(this, "Đăng nhập thất bại vui lòng thử lại", Toast.LENGTH_LONG).show();
            }
        });

        txtRegistration.setOnClickListener(view -> {
            startActivity(new Intent(this, Registration.class));
        });
    }

    private boolean isLogin(String username, String password)
    {
        try ( SQLiteDatabase db = dbContext.getReadableDatabase() ) {
            String query = "select username,role from AppUser where username = ? and password = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username,password});
            if(cursor != null){
                cursor.moveToFirst();
                @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex("role"));
                editor.putBoolean("is_login", true);
                editor.putString("username", username);
                editor.putString("role", role);
                editor.commit();
                cursor.close();
                return true;
            }
            else editor.putBoolean("is_login", false);
        }catch (Exception e) { return false; }
        editor.commit();
        return false;
    }

    private void setControl() {
        txtUsername = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegistration = findViewById(R.id.txtRegistration);
    }

    public AppUser getUserLogin(String username, String password) {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<AppUser> list = new ArrayList<>();

            String query = "select * from AppUser where username = ? and password = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username,password});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AppUser user = new AppUser(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4), cursor.getString(5));
                list.add(user);
                cursor.moveToNext();
            }
            db.close();
            return list.get(0);

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}