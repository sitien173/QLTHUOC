package com.ptithcm.qlthuoc;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.ptithcm.qlthuoc.Entity.AppUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends ArrayAdapter<AppUser> {

    Context context;
    DbContext dbContext = null;
    int resource;
    ArrayList<AppUser> data;
    AppUser userLogin;

public AdapterUser(@NonNull Context context, int resource, @NonNull ArrayList<AppUser> objects, DbContext dbContext) {
        super(context, resource, objects);
        this.context=context;
        this.data = objects;
        this.resource = resource;
        this.dbContext = dbContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource,null);

        ImageView ivImage = convertView.findViewById(R.id.ivImage);
        TextView tvName = convertView.findViewById(R.id.tvName);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        ImageButton btnDel = convertView.findViewById(R.id.btnDel);

        AppUser user = data.get(position);

        if(user.getAvatar() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(user.getAvatar(), 0, user.getAvatar().length);
            ivImage.setImageBitmap(bmp);
        }
        else{
            ivImage.setImageResource(R.drawable.avatar);
        }

        tvName.setText(user.getUsername());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileUser.class);

                intent.putExtra("edtUserName", user.getUsername());
                intent.putExtra("edtHoTen", user.getHoten());
                intent.putExtra("edtPassword", user.getPassword());
                intent.putExtra("role", user.getRole());
//                context.startActivity(intent);
                ((ListUser)context).startActivityForResult(intent,1);

            }
        });
//        btnDel.setOnClickListener(view -> {
//            dialogDelUser(user,position);
//        });

        btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                dialogDelUser(user,position);
            }
        });

        return convertView;
    }


    private void dialogDelUser(AppUser user, int position){
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        //Thiết lập tiêu đề
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có chắc chắn muốn xóa " + user.getUsername() +" vi tri " + position);
        // Nút Ok
        b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteUser(user,position);
                dialog.cancel();
            }
        });
        //Nút Cancel
        b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
    }

    private void deleteUser (AppUser user,int position){
        try (SQLiteDatabase db = dbContext.getWritableDatabase()) {
            db.delete("AppUser", "username=?", new String[]{user.getUsername()});
            db.close();
            data.remove(position);
            notifyDataSetChanged();

            Toast.makeText(context, "Đã xóa " + user.getUsername(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_LONG).show();
        }
    }
}