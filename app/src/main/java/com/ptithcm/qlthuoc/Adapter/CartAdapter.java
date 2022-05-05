package com.ptithcm.qlthuoc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.ListUser;
import com.ptithcm.qlthuoc.Order.AddInfoCustomer;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.Order.OrderSuccess;
import com.ptithcm.qlthuoc.Order.ProductOrder;
import com.ptithcm.qlthuoc.ProfileUser;
import com.ptithcm.qlthuoc.R;
import com.ptithcm.qlthuoc.RecyclerListener;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {

    Context context;
    private Activity activity;
    private List<CT_BanLe> listBanLe = new ArrayList<>();
    private AppUser customer;
    DbContext dbContext = null;
    int resource;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<CT_BanLe> listBanLe, DbContext dbContext){
        this.listBanLe = listBanLe;
        this.context  = context;
        this.resource = resource;
        this.dbContext = dbContext;
    }

    @Override
    public int getCount() {
        return listBanLe.toArray().length;
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
        TextView txtQuantity = (TextView) convertView.findViewById(R.id.txtQuantity);
        Button btnDelOrderLine = (Button) convertView.findViewById(R.id.btnDelOrderLine);

        CT_BanLe banLe = listBanLe.get(i);
        txtDrugName.setText(banLe.getThuoc().getTenthuoc());
        txtDrugPrice.setText(String.valueOf(banLe.getThuoc().getDongia() * banLe.getSoluong()));
        txtQuantity.setText("SL: " + banLe.getSoluong());
        btnDelOrderLine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        return convertView;
    }
}
