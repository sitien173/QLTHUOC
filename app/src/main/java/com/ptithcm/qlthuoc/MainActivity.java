package com.ptithcm.qlthuoc;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ptithcm.qlthuoc.Entity.AppUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText txtUsername,txtPassword;
    Button btnLogin;
    DbContext dbContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setConfig();
        setControl();
        setEvent();

        // redirect
        if(sharedPreferences.getBoolean("is_login", false)){
            Toast.makeText(this, "Bạn đã login", Toast.LENGTH_LONG);
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
            Boolean islogin = isLogin(username,password);
            if(islogin)
            {
                // redirect to dashboard
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_LONG);
            }

            else
            {
                Toast.makeText(this, "Đăng nhập thất bại vui lòng thử lại", Toast.LENGTH_SHORT);
            }
        });
    }

    private boolean isLogin(String username, String password)
    {
        try ( SQLiteDatabase db = dbContext.getReadableDatabase() ) {
            String query = "select username,role from [AppUser] where username = '"+username+"' and password = '"+password+"'";
            Cursor cursor = db.rawQuery(query, null);
            if(cursor != null && cursor.getCount()>0){
                @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex("role"));
                editor.putBoolean("is_login", true);
                editor.putString("username", username);
                editor.putString("role", role);
                editor.commit();
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
    }
}