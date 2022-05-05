package com.ptithcm.qlthuoc.Adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.ListUser;
import com.ptithcm.qlthuoc.MainActivity;
import com.ptithcm.qlthuoc.Order.AddInfoCustomer;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.Order.OrderSuccess;
import com.ptithcm.qlthuoc.Order.ProductOrder;
import com.ptithcm.qlthuoc.ProfileUser;
import com.ptithcm.qlthuoc.R;
import com.ptithcm.qlthuoc.RecyclerListener;

import java.util.ArrayList;
import java.util.List;

public class OrderLineAdapter extends BaseAdapter {

    Context context;
    private Activity activity;
    private List<Thuoc> listDrug = new ArrayList<>();
    private AppUser customer;
    DbContext dbContext;
    int resource;

    public OrderLineAdapter(@NonNull Context context, int resource, @NonNull List<Thuoc> listDrug, AppUser customer, DbContext dbContext){
        this.listDrug = listDrug;
        this.customer = customer;
        this.context  = context;
        this.resource = resource;
        this.dbContext = dbContext;
    }

    @Override
    public int getCount() {
        return listDrug.toArray().length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        // map to view
        TextView txtDrugName = (TextView) convertView.findViewById(R.id.txtDrugName);
        TextView txtDrugPrice = (TextView) convertView.findViewById(R.id.txtDrugPrice);
        Button btnAddProduct = (Button) convertView.findViewById(R.id.btnAddProduct);
        ImageView imgDrug = (ImageView) convertView.findViewById((R.id.imgThuoc));

        Thuoc drug = listDrug.get(i);

        txtDrugName.setText(drug.getTenthuoc());
        txtDrugPrice.setText(String.valueOf(drug.getDongia()));
        if(drug.getHinhanh() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(drug.getHinhanh(), 0, drug.getHinhanh().length);
            imgDrug.setImageBitmap(bmp);
        } else{
            imgDrug.setImageResource(R.drawable.medicine);
        }
        btnAddProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try (SQLiteDatabase db = dbContext.getWritableDatabase())
                {
                    // status: 2 (Chưa lập hóa đơn)
                    ContentValues ct = new ContentValues();
                    ct.put("id_thuoc", drug.getId());
                    ct.put("soluong", 1);
                    ct.put("total", 1 * drug.getDongia());
                    ct.put("status", 2);
                    ct.put("id_customer", customer.getId());
                    db.insert("CT_BanLe",null, ct);

                    Intent intent = new Intent(context, Order.class);
                    intent.putExtra("customer", customer);
                    ((ProductOrder)context).startActivityForResult(intent,1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
}
