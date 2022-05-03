package com.ptithcm.qlthuoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ptithcm.qlthuoc.Entity.AppUser;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class ListUser extends AppCompatActivity {

    ListView lsvUser;
    ImageButton btnEdit;
    ImageButton btnDel;

    ArrayList<AppUser> arrlistUser = new ArrayList<>();
    AdapterUser adapterUser;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DbContext dbContext;

    AppUser userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        lsvUser =  (ListView) findViewById(R.id.lsvUser);
//        btnDel = findViewById(R.id.btnDel);
//        btnEdit = findViewById(R.id.btnEdit);
        System.out.println(" list user");
        setConfig();
        // get data in database
        arrlistUser = getAllUsers();
        setEvent();
        Intent intent = getIntent();

    }
    private void setEvent() {

        adapterUser = new AdapterUser(this, R.layout.layout_item, arrlistUser,dbContext);
        lsvUser.setAdapter(adapterUser);

        lsvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println("bạn chọn" + position);
                Toast.makeText(ListUser.this, "bạn chọn"+ position, Toast.LENGTH_SHORT).show();
            }
        });

        lsvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                XoaUser(arrlistUser.get(position));
                return false;
            }
        });

    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbContext = new DbContext(this);
    }

    public ArrayList<AppUser> getAllUsers() {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<AppUser> list = new ArrayList<>();

            String query = "select * from AppUser";
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
//                String username, String password, String hoten, byte[] avatar, String role
                AppUser user = new AppUser(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                list.add(user);
                cursor.moveToNext();
            }
            return list;

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode == 3) {
                AppUser userNew = (AppUser) data.getSerializableExtra("item");
                AppUser userOld = (AppUser) data.getSerializableExtra("itemOld");

//                System.out.println("avatarNew: " + Arrays.toString(userNew.getAvatar()));
//                System.out.println("avatarOld: " + Arrays.toString(userOld.getAvatar()));

                EditUser(userNew,userOld);
                Toast.makeText(this, "Da cap nhat", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void EditUser(AppUser userNew,AppUser userOld) {
        for (AppUser item : arrlistUser ) {
            if(item.getUsername().equals(userOld.getUsername()) && item.getHoten().equals(userOld.getHoten()) ) {
                item.setUsername(userNew.getUsername());
                item.setHoten(userNew.getHoten());
                item.setPassword(userNew.getPassword());
                item.setAvatar(userNew.getAvatar());
                item.setRole(userNew.getRole());

//                System.out.println("User Edit: ");
//                System.out.println(user.getUsername());
//                System.out.println(user.getHoten());
//                System.out.println(user.getPassword());
//                System.out.println(user.getRole());
            }
        }
        adapterUser.notifyDataSetChanged();
    }

}