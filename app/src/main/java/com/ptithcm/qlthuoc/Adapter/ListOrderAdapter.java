package com.ptithcm.qlthuoc.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.HoaDon;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.Order.AdminListOrder;
import com.ptithcm.qlthuoc.Order.DetailOrder;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.Order.ProductOrder;
import com.ptithcm.qlthuoc.R;

import java.util.ArrayList;
import java.util.List;

public class ListOrderAdapter extends BaseAdapter {
    Context context;
    private Activity activity;
    private List<HoaDon> listHoaDon = new ArrayList<>();
    private AppUser customer;
    DbContext dbContext = null;
    int resource;

    public ListOrderAdapter(@NonNull Context context, int resource, @NonNull List<HoaDon> listHoaDon, DbContext dbContext){
        this.listHoaDon = listHoaDon;
        this.context  = context;
        this.resource = resource;
        this.dbContext = dbContext;
    }

    @Override
    public int getCount() {
        return listHoaDon.toArray().length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public float getTotalOrder(List<CT_BanLe> listCTBanLe) {
        float totalOrder = 0;
        for (CT_BanLe ctBanLe : listCTBanLe) {
            totalOrder += ctBanLe.getTotal();
        }
        return totalOrder;
    }

    @SuppressLint("Range")
    public ArrayList<CT_BanLe> getListBanLe(int id_hoadon) {
        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<CT_BanLe> listBanLe = new ArrayList<>();
            String query = "SELECT * FROM CT_BanLe WHERE id_hoadon = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id_hoadon)});
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                int COLUMN_INDEX_ID_THUOC = cursor.getColumnIndex("id_thuoc");

                // tạo thuốc
                String queryDrug = "SELECT * FROM Thuoc WHERE id = ?";
                Cursor cursorDrug = db.rawQuery(queryDrug, new String[]{String.valueOf(cursor.getString(COLUMN_INDEX_ID_THUOC))});
                cursorDrug.moveToFirst();
                byte[] imageDrug = cursorDrug.getBlob(5);
                Thuoc newDrug = new Thuoc(cursorDrug.getInt(0), cursorDrug.getString(1), cursorDrug.getString(2), cursorDrug.getString(3), cursorDrug.getInt(4), cursorDrug.getFloat(6));
                newDrug.setHinhanh(imageDrug);

                // tạo chi tiết đơn hàng
                CT_BanLe ctBanLe = new CT_BanLe(newDrug, null, cursor.getInt(cursor.getColumnIndex("soluong")), cursor.getFloat(cursor.getColumnIndex("total")), cursor.getInt(cursor.getColumnIndex("status")), customer);
                listBanLe.add(ctBanLe);

                cursor.moveToNext();
            }
            return listBanLe;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        // map to view
        TextView txtCustomerName = (TextView) convertView.findViewById(R.id.txtCustomerName);
        TextView txtTotalOrder = (TextView) convertView.findViewById(R.id.txtTotalOrder);
        Button btnDetailOrder = (Button) convertView.findViewById(R.id.btnDetailOrder);

        HoaDon hoaDon = listHoaDon.get(i);
        txtCustomerName.setText(hoaDon.getKhachhang().getHoten());

        ArrayList<CT_BanLe> listCTBanLe = getListBanLe(hoaDon.getId());
        float toalOrder = getTotalOrder(listCTBanLe);
        txtTotalOrder.setText(String.valueOf(toalOrder));

        btnDetailOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailOrder.class);
                intent.putExtra("customer", hoaDon.getKhachhang());
                intent.putExtra("id_hoadon", hoaDon.getId());
                ((AdminListOrder)context).startActivityForResult(intent,1);
            }
        });
        return convertView;
    }
}
