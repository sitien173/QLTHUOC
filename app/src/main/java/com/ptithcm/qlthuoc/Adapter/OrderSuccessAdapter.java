package com.ptithcm.qlthuoc.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.R;

import java.util.ArrayList;
import java.util.List;

public class OrderSuccessAdapter extends BaseAdapter {
    Context context;
    private Activity activity;
    private List<CT_BanLe> listBanLe = new ArrayList<>();
    private AppUser customer;
    DbContext dbContext = null;
    int resource;

    public OrderSuccessAdapter(@NonNull Context context, int resource, @NonNull List<CT_BanLe> listBanLe, DbContext dbContext){
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
        ImageView imageDrug = (ImageView) convertView.findViewById(R.id.imageDrug);

        CT_BanLe banLe = listBanLe.get(i);
        txtDrugName.setText(banLe.getThuoc().getTenthuoc());
        txtDrugPrice.setText(String.valueOf(banLe.getThuoc().getDongia() * banLe.getSoluong()) + "VNĐ");
        txtQuantity.setText("SL: " + banLe.getSoluong());
        // set image order line
        if(banLe.getThuoc().getHinhanh() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(banLe.getThuoc().getHinhanh(), 0, banLe.getThuoc().getHinhanh().length);
            imageDrug.setImageBitmap(bmp);
        } else{
            imageDrug.setImageResource(R.drawable.medicine);
        }
        return convertView;
    }
}
